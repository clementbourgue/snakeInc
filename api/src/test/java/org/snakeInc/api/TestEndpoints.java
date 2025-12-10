package org.snakeInc.api;

import org.junit.jupiter.api.Test;
import org.snakeInc.api.entities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = ApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class TestEndpoints {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // DTO utilisé uniquement pour les requêtes de test
    static class PlayerParams {
        public String name;
        public int age;

        public PlayerParams(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    private String baseUrl(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void postThenGetPlayer_shouldReturnSamePlayer() {
        // 1) POST /api/v1/players
        PlayerParams body = new PlayerParams("Clément", 22);

        ResponseEntity<Player> postResponse = restTemplate.postForEntity(
                baseUrl("/api/v1/players"),
                body,
                Player.class
        );

        // Vérifications POST
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Player created = postResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isGreaterThan(0);
        assertThat(created.getName()).isEqualTo("Clément");
        assertThat(created.getAge()).isEqualTo(22);
        assertThat(created.getCategory()).isEqualTo("SENIOR");

        int id = created.getId();

        // 2) GET /api/v1/players/{id}
        ResponseEntity<Player> getResponse = restTemplate.getForEntity(
                baseUrl("/api/v1/players/" + id),
                Player.class
        );

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Player fetched = getResponse.getBody();
        assertThat(fetched).isNotNull();
        assertThat(fetched.getId()).isEqualTo(id);
        assertThat(fetched.getName()).isEqualTo("Clément");
        assertThat(fetched.getAge()).isEqualTo(22);
    }

    @Test
    void postPlayer_withInvalidData_shouldReturnBadRequest() {
        // name vide + age <= 13 ==> doit déclencher la validation

        PlayerParams invalidBody = new PlayerParams("", 10);

        ResponseEntity<String> response = restTemplate.postForEntity(
                baseUrl("/api/v1/players"),
                invalidBody,
                String.class // on prend String pour inspecter le JSON brut si besoin
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        String responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody).contains("Validation failed");
    }
}
