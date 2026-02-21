## Learning Hibernate with Spring

This repository contains a simple educational project designed to reinforce fundamental concepts of 
**Hibernate** and **Spring Data JPA** in a **Spring** application, using **PostgreSQL** as the DBMS.

### Purpose

The main goal of this project is to provide a practical example for learning how to:

- Configure Hibernate with Spring.
- Map Java entities to database tables using JPA annotations.
- Work with different types of entity relationships (e.g., One-to-Many, Many-to-One).
- Use Spring Data JPA repositories for database operations.
- Interact with a PostgreSQL database.
- Use Docker & Docker Compose for containerization.

### Technology Stack

- Language: Java
- Framework: Spring
- ORM: Hibernate (via Spring Data JPA)
- Database: PostgreSQL
- Containerization: Docker, Docker Compose
- Build Tool: Maven

### Getting Started

#### Prerequisites

- Java Development Kit (JDK) 17 or later.
- Maven or Gradle.
- Docker and Docker Compose installed on your machine.

#### Setup

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/ivan-kulik/hibernate-Coursework.git
    ```
2.  **Build the application:**
    ```bash
    mvn clean package
    # or with Gradle: ./gradlew bootJar
    ```
3.  **Run with Docker Compose:**
    ```bash
    docker-compose up -d
    ```
    This command will:
    - Start a PostgreSQL container.
    - Build and start the Spring application container.
    - Connect the application to the database.

4.  **View logs:**
    ```bash
    docker-compose logs -f app
    ```

5.  **Stop the application:**
    ```bash
    docker-compose down
    ```
    To also remove the database volume (reset all data):
    ```bash
    docker-compose down -v
    ```

#### Docker Configuration Files

##### Dockerfile

The `Dockerfile` in the root directory is used to build the Spring application image:

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### License

This project is for educational purposes and is open-sourced under the [MIT license](LICENSE).