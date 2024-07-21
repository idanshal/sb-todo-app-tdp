# Spring Boot based Todo app for TDP


DI
Remove @Autowired from class member dependency -> observe dependency is null
Remove @Service from dependency class -> observe

## Generate a Spring Boot project using Maven Initializr
- Go to https://start.spring.io/
- Add dependencies
  - Spring Web
  - Spring Data JPA
  - H2 Database
  - Validation
- Fill in project metadata and generate

## Project structure
- Open project in Intellij IDE
- Review project files structure
- Review pom.xml

## Compilation
- Verify project bootstraps successfully
- Review application.yml and modify port
```yaml
- server.port: 8081
```

- Add the following packages
  - dto
  - controllers
  - services
  - dal

## Define models and entities

- Create TodoRequest
- Create TodoEntity

## Configure Database

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

## @Controller and @RestController

Create TodoController
@RestController
@RequestMapping
Add endpoints
getTodos
getTodo
createTodo
updateTodo
deleteTodo
Create TodoService
Create TodoRepository
