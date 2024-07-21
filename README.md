# Spring Boot based Todo app for TDP

## Generate a Spring Boot project using Maven Initializr
- Go to https://start.spring.io/
- Add dependencies
  - Spring Web
  - Spring Data JPA
  - H2 Database
  - Validation
- Fill in project metadata and generate

![spring initializr](course_data/images/spring_initializr_screenshot.png)

## Project structure
- Open project in Intellij IDE
- Review project files structure
- Review pom.xml
  - Inspect dependencies
- Review @SpringBootApplication

## Compile & Run
- Verify the application compiles and runs successfully
  - View logs in the console
  - Note that application server (Tomcat) starts on port 8080
- Review application.yml and set the port to 8081
```yaml
server:
  port: 8081
```

- Add packages: dto, controllers, services, dal

  
## Define models and entities
- Create TodoRequest (String title, String description, Boolean isCompleted)
- Create TodoEntity (Long id, String title, String description, Boolean isCompleted)

## Create TodoRepository
```java
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}
```

## Configure Database
We will work with the H2 in-memory database for this project.
- Add the following yaml section to application.yml 
```yaml
spring:
  h2:
    console:
      enabled: true
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: tdp
    password: tdp
  jpa:
    hibernate:
      ddl-auto: update
```
- Run the application and access the H2 console at http://localhost:8081/h2-console
- Verify you have an empty TODO_ENTITY table

## Play with DI
- Create TodoController
- Create TodoService
- Instruct Spring to manage instances of both classes
- Instruct Spring to inject TodoService into TodoController
- Remove @Autowired from class member dependency
  - Observe dependency is null
- Remove @Service from dependency class
  - Observe Spring Boot does not bootstrap successfully


## Building our Controller layer

### Request mappings
@RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping map web requests to Spring Controller methods.

- Add @RequestMapping to the controller class with the path "/api/todos"

@RestController
- Add the following endpoints:
  - getTodos   
  - getTodo
  - createTodo
  - updateTodo
  - deleteTodo

## Creating the Service layer
- Create TodoService
## Creating the Repo layer
- Create TodoRepository

## General best practices
- Use DTOs to transfer data between layers (SoC)
- Use Lombok to reduce boilerplate code
- Prefer constructor injection over field injection (remember - you don't need @Autowired)
- Use Java Streams to manipulate collections


