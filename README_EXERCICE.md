# Projet Graines Potagères

## Description

Application Spring Boot permettant de gérer un système de commande de graines potagères. Les jardiniers peuvent commander des sachets de graines et envoyer des recettes. Les fournisseurs peuvent définir les sachets qu'ils proposent.

## Structure du projet

### Entités métier (package `com.visiplus.graines.business`)

Le modèle de données comprend les entités suivantes :

- **Famille** : Représente une famille de plantes (ex: aromatiques, fruits)
- **TypeDeGraine** : Type de graine avec ses caractéristiques (nom, conseils, période de plantation)
- **Fournisseur** : Fournisseur de sachets de graines
- **Sachet** : Sachet de graines avec prix et quantité
- **Jardinier** : Utilisateur qui commande des graines
- **Commande** : Commande passée par un jardinier
- **LigneDeCommande** : Ligne d'une commande (sachet + quantité)
- **Recette** : Recette utilisant différents types de graines

### Validations implémentées

Les validations suivantes ont été mises en place conformément aux exigences :

- La couleur de la famille doit contenir exactement 6 caractères
- Les semaines de plantation doivent être comprises entre 1 et 52
- L'intitulé de la recette ne doit pas être vide
- Les conseils concernant le type de graine doivent contenir au minimum 40 caractères
- Le numéro de téléphone du fournisseur doit débuter par 06 ou par 07
- Le type de graine doit avoir un nom unique
- Le type de graine doit obligatoirement être associé à une famille
- La commande doit avoir au moins une ligne de commande
- La date de naissance doit être dans le passé

### Repositories (package `com.visiplus.graines.repository`)

Les repositories incluent les méthodes de requêtes personnalisées suivantes :

1. Lister toutes les commandes envoyées entre deux dates données
2. Donner les sachets qui n'ont jamais été commandés
3. Lister tous les jardiniers ayant déjà commandé du basilic
4. Donner les sachets triés sur la quantité commandée décroissante
5. Donner les types de graine qu'il est possible de planter aujourd'hui
6. Donner les recettes triées sur le nombre de types de graine
7. Lister les jardiniers triés sur le nombre de commandes décroissant
8. Lister les jardiniers de plus de 60 ans
9. Donner les commandes triées sur le montant total décroissant
10. Donner le nombre de commandes par mois

### Service

L'interface `TypeDeGraineService` et son implémentation `TypeDeGraineServiceImpl` fournissent les opérations CRUD sur les types de graine.

### API REST

Le contrôleur `TypeDeGraineController` expose les endpoints suivants :

- `GET /api/types-de-graine/{id}` : Récupérer un type de graine par son ID
- `GET /api/types-de-graine` : Récupérer une page de types de graine
- `POST /api/types-de-graine` : Créer un nouveau type de graine
- `PUT /api/types-de-graine/{id}` : Modifier un type de graine existant
- `DELETE /api/types-de-graine/{id}` : Supprimer un type de graine

L'API est documentée avec Swagger/OpenAPI.

### Aspect de logging

La classe `LoggingAspect` intercepte toutes les méthodes du service pour logger :
- L'entrée dans la méthode avec les paramètres
- La sortie de la méthode avec le résultat
- Les exceptions éventuelles
- Le temps d'exécution de chaque méthode

### Initialisation des données

La classe `AjoutDonneesInitiales` initialise la base de données au démarrage avec :
- 2 familles (les aromatiques, les fruits)
- 2 types de graine (basilic, tomate Roma)
- 1 sachet de basilic
- 1 jardinier né le 01/01/1950
- 1 commande avec 2 sachets de basilic

Elle affiche également les statistiques en exécutant les 10 requêtes personnalisées.

## Configuration

### Base de données H2

La configuration dans `application.properties` utilise une base H2 en mémoire :

```properties
spring.datasource.url=jdbc:h2:mem:graines
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

### Dépendances Maven

Le projet utilise Spring Boot 3.2.0 avec les dépendances suivantes :
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- spring-boot-starter-web
- spring-boot-starter-aop
- h2
- lombok
- springdoc-openapi-starter-webmvc-ui

## Lancement de l'application

### Prérequis

- Java 21
- Maven (ou utiliser le wrapper Maven fourni)

### Commandes

```bash
# Compiler le projet
.\mvnw.cmd clean compile

# Lancer l'application
.\mvnw.cmd spring-boot:run
```

## Accès aux interfaces

Une fois l'application démarrée :

- **Swagger UI** : http://localhost:8080/swagger-ui/index.html
- **Console H2** : http://localhost:8080/h2-console
  - JDBC URL : `jdbc:h2:mem:graines`
  - Username : `sa`
  - Password : (laisser vide)

## Vérification

Au démarrage de l'application, la console affiche :
1. La création des données initiales
2. Les résultats des 10 requêtes statistiques
3. Les logs de l'aspect pour chaque appel de méthode du service

## Exemples d'utilisation de l'API

### Récupérer tous les types de graine

```bash
curl http://localhost:8080/api/types-de-graine
```

### Récupérer un type de graine par ID

```bash
curl http://localhost:8080/api/types-de-graine/1
```

### Créer un nouveau type de graine

```bash
curl -X POST http://localhost:8080/api/types-de-graine \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "carotte",
    "conseils": "La carotte préfère un sol léger et bien drainé. Semez en ligne et éclaircissez régulièrement.",
    "semaineDebut": 10,
    "semaineFin": 30,
    "famille": {"id": 2}
  }'
```

## Technologies utilisées

- **Spring Boot** : Framework principal
- **Spring Data JPA** : Accès aux données
- **Hibernate** : ORM
- **H2 Database** : Base de données en mémoire
- **Bean Validation** : Validation des données
- **Spring AOP** : Programmation orientée aspect
- **Lombok** : Réduction du code boilerplate
- **SpringDoc OpenAPI** : Documentation de l'API
