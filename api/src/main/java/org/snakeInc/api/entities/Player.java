package org.snakeInc.api.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // wrapper Integer recommandé pour @GeneratedValue

    private String name;

    private int age;

    private String category;

    private LocalDateTime createdAt;

    // Constructeur vide requis par JPA (au moins protected)
    protected Player() {
    }

    // Constructeur utilisé par ton code applicatif
    public Player(String name, int age) {
        this.name = name;
        this.age = age;
        this.category = age < 18 ? "JUNIOR" : "SENIOR";
        this.createdAt = LocalDateTime.now();
    }

    // ===== GETTERS =====

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCategory() {
        return category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Score> scores = new ArrayList<>();
}
