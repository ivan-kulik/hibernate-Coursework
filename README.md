## Learning Hibernate with Spring

This repository contains a simple educational project designed to reinforce fundamental concepts of 
**Hibernate** in a **Spring** application, using **PostgreSQL** as the DBMS.

### Purpose

The main goal of this project is to provide a practical example for learning how to:

- Configure Hibernate with Spring.
- Map Java entities to database tables using JPA annotations.
- Work with different types of entity relationships (e.g., One-to-Many, Many-to-One).
- Interact with a PostgreSQL database.
- Use Docker for containerization.

### Technology Stack

- Language: Java
- Framework: Spring
- ORM: Hibernate
- Database: PostgreSQL
- Containerization: Docker
- Build Tool: Maven

### Getting Started

#### Prerequisites

- Java Development Kit (JDK) 24 or later.
- Maven.
- Docker installed on your machine.

#### Setup

1. Clone the repository:
    ```bash
    git clone https://github.com/ivan-kulik/hibernate-Coursework.git
    ```
2. Setup database \
Start by launching the PostgreSQL database in a Docker container. 
This setup uses a named volume (`postgres_data`) to ensure your database tables and records persist even if the container is stopped or removed.

Start the database:
   ```bash
   docker run -d \
     --name course-db \
     -p 5432:5432 \
     -e POSTGRES_USER=postgres \
     -e POSTGRES_PASSWORD=root \
     -e POSTGRES_DB=course_db \
     -v postgres_data:/var/lib/postgresql/data \
     postgres:15-alpine
   ```
Database management commands:

* Stop the database container:
   ```bash
   docker stop course-db
    ```
* Remove the database container (this does not delete your saved data):
   ```bash
   docker rm course-db
    ```
* Reset all data (Delete the volume): \
If you want to completely wipe the database and start fresh, 
stop and remove the container first, then delete the volume:
   ```bash
   docker stop course-db
   docker rm course-db
   docker volume rm postgres_data
    ```

3. Build the application:
    ```bash
    mvn clean package
    ```
   
4. Run the application:
   ```bash
   java -jar target/hibernate-Coursework-1.0-SNAPSHOT.jar
    ```

### License

This project is for educational purposes and is open-sourced under the [MIT license](LICENSE).