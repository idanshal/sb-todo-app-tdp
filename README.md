# Spring Boot based Todo app for TDP

## Generate a Spring Boot project using Maven Initializr
- Go to https://start.spring.io/
- Add dependencies
  - Spring Web
  - Spring Data JPA
  - H2 Database
- Fill in project metadata and generate

![spring initializr](course_data/images/spring_initializr_screenshot.png)

## Project structure
- Open project in Intellij IDE
- Review project files structure
- Review pom.xml
  - Inspect dependencies
- Review `@SpringBootApplication`
  - From SpringApplication.run, the application will create the application context, that contains all the required Beans. 
  - In the case of using a Web starter, it will also create an instance of Tomcat web server.
```java
@SpringBootApplication
public class TodoAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}
}
```

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

## Play with DI (git branch: 01-di)

`@Component` is a generic stereotype for any Spring-managed component.
`@Repository`, `@Service`, and `@Controller` are specializations of `@Component` for more specific use cases (in the persistence, service, and presentation layers, respectively).

- Create TodoController
- Create TodoService
- Instruct Spring to manage instances of both classes
- Instruct Spring to inject TodoService into TodoController
- Remove `@Autowired` from class member dependency
  - Observe dependency is null
- Remove `@Service` from dependency class
  - Observe Spring Boot does not bootstrap successfully
- Finally, replace `@Autowired` with constructor injection


## Building our Controller layer and adding our BL (git branch: 02-bl)

### Request mappings
`@RequestMapping`, `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` map web requests to Spring Controller methods.

### Request Binding
- `@PathVariable` - Binds a URI template variable to a method parameter
- `@RequestParam` - Binds a query parameter to a method parameter
- `@RequestBody` - Binds the HTTP request body to a method parameter

### Response Handling
- `ResponseEntity` represents the whole HTTP response: status code, headers, and body. 
  - As a result, we can use it to fully configure the HTTP response. 
  - If we want to use it, we have to return it from the endpoint; Spring takes care of the rest.
- `@ResponseStatus` - Sets the status code of the HTTP response
- `@ResponseBody` - Binds a method return value to the response body

- Add `@RequestMapping` to the controller class with the path "/api/todos"
- Add the following endpoints:

| Method Name | Http Verb | Endpoint        | Returns                              |
|-------------|-----------|-----------------|--------------------------------------|
| getTodos    | GET       | /api/todos      | ResponseEntity\<List\<TodoEntity\>\> |
| getTodo     | GET       | /api/todos/{id} | ResponseEntity\<TodoEntity\>         |
| createTodo  | POST      | /api/todos      | ResponseEntity\<TodoEntity\>         |
| updateTodo  | PUT       | /api/todos/{id} | ResponseEntity\<TodoEntity\>         |
| deleteTodo  | DELETE    | /api/todos/{id} | ResponseEntity\<Void\>               |

- Finally, let's test the endpoints using Postman

## Adding error handling (git branch: 03-error-handling)

### @ExceptionHandler

`@ExceptionHandler` is a Spring annotation that provides a mechanism to treat exceptions thrown during execution of 
handlers (controller operations).

```java
public class FooController{
//...
@ExceptionHandler({ CustomException1.class, CustomException2.class })
public void handleException() { //
}
}
```
If we enter this annotation on methods of controller classes, 
it will serve as the entry point for handling exceptions thrown within this controller only.


---
`@RestController` is a specialized version of the controller.
It includes the `@Controller` and `@ResponseBody` annotations, and as a result, simplifies the controller implementation.


## General guidelines
- Use DTOs to transfer data between layers (SoC)
- Use Lombok to reduce boilerplate code
- Prefer constructor injection over field injection (remember - you don't need @Autowired)
- Use Java Streams to manipulate collections
- More to explore
  - Spring AOP for cross-cutting concerns
  - `@Async` for asynchronous processing
  - `@Scheduled` for scheduled tasks
  - `@Transactional` for transaction management
  - Filters & Interceptors for request/response manipulation
- Use ObjectMapper to serialize/deserialize objects to/from JSON
- 


