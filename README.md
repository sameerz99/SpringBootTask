
# 10 days task in Spring Boot
Recommended: Spring boot version 2.5, java version 11, latest MySQL, Postman

### Task 1: Set up a Spring boot Project
- Study Spring Boot basics and the concept of auto-configuration. --> Completed
- Create a new Spring Boot project via Spring Initializr, selecting dependencies for Spring Web, Spring Data JPA, MySQL Driver, and Spring Boot Test. --> Completed
- Configure the application to connect to your MySQL database. --> Completed

### Task 2: Create Entities and a Many-to-Many Relationship
- Define two entities, Author and Book, where an author can write multiple books, and a book can have multiple authors (a many-to-many relationship). --> Completed
- Implement the entities with JPA annotations, including @ManyToMany, and configure the join table. --> Completed
- Develop repositories for both entities using Spring Data JPA.--> Completed
- Insert data into database using postman. --> Completed

### Task 3: Develop REST API Endpoints to Manage Authors and Books
- Implement RESTful endpoints that enable CRUD operations for Author and Book entities.--> Completed
- Provide additional endpoints to manage the many-to-many relationships between authors and books, such as adding or removing books from an author's list (and vice versa).--> Completed
