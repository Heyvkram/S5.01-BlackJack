# IT ACADEMY BACKEND JAVA SPECIALIZATION

## SPRINT 5 TASK 1 

### Summary

This is a Spring Boot WebFlux project built with Maven as the dependency manager. It implements a Blackjack API, managing data across both MongoDB and MySQL databases. The application supports full Blackjack gameplay, player management, card handling, and core game rules. It includes API documentation using Swagger and unit tests. The application can also be Dockerized.

### Technologies Used

- Java JDK (version 22)
- Spring Boot (WebFlux)
- Spring Data MongoDB (Reactive)
- R2DBC (for MySQL)
- Maven
- Swagger/OpenAPI
- JUnit
- Mockito
- WebTestClient

### Requirements

- Java JDK (version 22)
- Maven installed

### How to run the application

1.  Clone this repository or download the project.
2.  Configure MongoDB and MySQL connection details in `application.properties`.
3.  Open a terminal in the project root directory.
4.  Run the following Maven command:
    ```bash
    ./mvnw spring-boot:run
    ```

### API Endpoints

API endpoints are documented using Swagger. You can access the documentation at:

URL: http://localhost:8080/webjars/swagger-ui/index.html.

*(Note: Specific API endpoints for Blackjack game actions, player management, etc., will be documented in the Swagger UI.)*

### Database Configuration

-   **MongoDB:** Used for game data (reactive).
-   **MySQL:** Used for player persistence.

### Testing

Unit tests are implemented using JUnit and Mockito. Controller tests for WebFlux are done using WebTestClient.
