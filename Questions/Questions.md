1) First Steps with the Snake

1.1) Java Fundamentals and OOO Concepts

- Class, object, primitive types :
  -- Une classe est un modèle (ou un plan) qui définit :
  des attributs → les données que possèdent les objets ;
  des méthodes → les actions qu’ils peuvent effectuer.
  Exemple ici : Snake, Game, Grid, Apple, ...
  -- Un objet est une instance d’une classe — c’est une version concrète du modèle.
  Exemple ici : grid objet de Grid
  -- Les types primitifs sont les types de base de Java.
  Ce ne sont pas des objets (ils ne viennent pas d’une classe), mais des valeurs simples et rapides à manipuler.
  Exemple : int, boolean, char, ...

- Encapsulation, properties, getter and setter, final.
  -- Encapsulation : regrouper les attributs et les méthodes qui les manipulent dans une même classe.
  Cela permet notamment de protéger les données internes.
  -- Les getters et setters sont des méthodes publiques permettant :
  de lire la valeur d’un attribut (getXxx) ;
  de modifier la valeur d’un attribut (setXxx).
  -- final sert à rendre quelque chose non modifiable.

- Instantiation of objects, Constructors.
  -- Instancier un objet, c’est créer une instance d’une classe — autrement dit, créer un objet réel à partir du “plan” (la classe).
  Exemple : Grid grid = new Grid()
  -- Un constructeur est une méthode spéciale qui est appelée automatiquement lors de la création d’un objet (new).
  Il sert à initialiser les attributs de l’objet.
  Exemple : public Snake(AppleEatenListener,Grid)

- Static fields, static methods. What are the particularity of "static" ?
  -- Le mot-clé static signifie que l’élément appartient à la classe elle-même et non à une instance (objet)
  Une méthode static appartient aussi à la classe, pas à un objet.
  Particularités : partagé, accessible sans instanciation et unique en mémoire.

- Composition.
  -- La composition en Java est une relation forte “fait partie de”, où une classe contient et gère le cycle de vie d’un autre objet intégré à elle.
  Exemple : Grid est dans Game.

- Inheritance, interface, polymorphism
  -- L’héritage permet à une classe (appelée classe fille ou sous-classe) de reprendre les attributs et méthodes d’une autre classe (classe mère ou super-classe).
  Mots-clés : extends → pour indiquer l’héritage de classe ;
  super → pour appeler le constructeur ou les méthodes de la classe mère.
  -- Une interface est une liste de méthodes abstraites (sans corps) que les classes doivent obligatoirement implémenter.
  Les interfaces permettent la modularité, la flexibilité et une forme de multiple inheritance (par contrat).
  Mots-clés : interface → pour déclarer une interface ;
  implements → pour la mettre en œuvre dans une classe.
  -- Polymorphisme : Un même nom de méthode peut avoir plusieurs comportements. Override, Overload.

- Static VS dynamic types.
  -- Le type statique d’une variable est connu à la compilation et détermine ce qu’on peut appeler, tandis que le type dynamique est connu à l’exécution et détermine quelle implémentation est réellement exécutée.

- Separation of concerns (design principle)
  La Separation of Concerns consiste à diviser un programme en parties distinctes, chacune responsable d’un aspect précis, afin de rendre le code plus clair, modulaire et facile à maintenir.
  Exemple : une classe Apple, une classe Snake, une classe Grid, ...

- Collections
  -- Les collections Java fournissent des structures de données puissantes et flexibles — List, Set, Queue, et Map — pour stocker, organiser et manipuler efficacement des groupes d’objets.

- Exceptions
  -- Une exception en Java est un événement anormal interrompant l’exécution normale d’un programme, géré à l’aide de blocs try-catch-finally, et permettant d’écrire du code plus sûr, robuste et contrôlé.
  Exemple : utilisation de throw dans la classe Snake.

- Functional interfaces / Lambda
  Une interface fonctionnelle définit un seul comportement abstrait, et une lambda est une façon concise de fournir ce comportement sous forme de fonction, rendant le code Java plus clair, modulaire et expressif.

- Lombok :
  -- Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.
  Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.

