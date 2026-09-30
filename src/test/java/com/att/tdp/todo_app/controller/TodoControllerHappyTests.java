package com.att.tdp.todo_app.controller;

import com.att.tdp.todo_app.helpers.TodoTestHelper;
import com.att.tdp.todo_app.repository.TodoRepository;
import com.att.tdp.todo_app.entity.TodoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // used for loading the full application context for integration tests.
@AutoConfigureMockMvc // used for configuring MockMvc for testing web layer components in a Spring Boot application
class TodoControllerHappyTests {

    @Autowired
    private MockMvcTester mockMvcTester;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    void setup() {
        todoRepository.deleteAll();
    }

    @Test
    void testGetTodosSuccess() {
        //arrange
        todoRepository.saveAll(List.of(
                TodoTestHelper.createTodoEntity("Do laundry", "Wash clothes"),
                TodoTestHelper.createTodoEntity("Do dishes", "Wash dishes")
        ));

        // act
        MvcTestResult result = mockMvcTester.get().uri("/api/todos").exchange();

        // assert
        assertThat(result).hasStatusOk();
        assertThat(result).bodyJson().extractingPath("$[*].title")
                .asArray().containsExactlyInAnyOrder("Do laundry", "Do dishes");
        assertThat(result).bodyJson().extractingPath("$[*].description")
                .asArray().containsExactlyInAnyOrder("Wash clothes", "Wash dishes");
    }

    @Test
    void testEmptyTodosSuccess() {
        // act
        assertThat(mockMvcTester.get().uri("/api/todos"))
                // assert
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$")
                .asArray()
                .isEmpty();
    }


    @Test
    void testGetTodoSuccess() {
        // arrange
        TodoEntity savedTodo = todoRepository.save(TodoTestHelper.createTodoEntity("Learn something new", "Read a book"));
        // act
        MvcTestResult result = mockMvcTester.get().uri("/api/todos/%d".formatted(savedTodo.getId())).exchange();

        // assert
        assertThat(result).hasStatusOk();
        assertThat(result).bodyJson().extractingPath("$.id").asNumber()
                .satisfies(id -> assertThat(id.longValue()).isEqualTo(savedTodo.getId()));
        assertThat(result).bodyJson().extractingPath("$.title").asString().isEqualTo(savedTodo.getTitle());
        assertThat(result).bodyJson().extractingPath("$.description").asString().isEqualTo(savedTodo.getDescription());
        assertThat(result).bodyJson().extractingPath("$.completed").asBoolean().isFalse();
    }

    @Test
    void testCreateTodoSuccess() {
        // act
        MvcTestResult result = mockMvcTester.post().uri("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                    {"title":"Test","description":"Test Description"}
                        """)
                .exchange();

        // assert
        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).bodyJson().extractingPath("$.title").asString().isEqualTo("Test");
        assertThat(result).bodyJson().extractingPath("$.description").asString().isEqualTo("Test Description");
        assertThat(result).bodyJson().extractingPath("$.completed").asBoolean().isFalse();

        assertThat(todoRepository.findAll()).singleElement().satisfies(todo -> {
            assertThat(todo.getTitle()).isEqualTo("Test");
            assertThat(todo.getDescription()).isEqualTo("Test Description");
            assertThat(todo.getCompleted()).isFalse();
        });
    }

    @Test
    void testUpdateTodo() {

        // arrange
        TodoEntity todo = new TodoEntity();
        todo.setTitle("Old Title");
        todo.setDescription("Some description");
        todoRepository.save(todo);

        // act
        assertThat(mockMvcTester.put().uri("/api/todos/" + todo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Title\"}"))
                // assert
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.title")
                .asString()
                .isEqualTo("Updated Title");

        assertThat(todoRepository.findById(todo.getId())).hasValueSatisfying(updatedTodo -> {
            assertThat(updatedTodo.getTitle()).isEqualTo("Updated Title");
            assertThat(updatedTodo.getDescription()).isEqualTo("Some description");
        });
    }

    @Test
    void testDeleteTodoSuccess() {
        // arrange
        TodoEntity savedTodo = todoRepository.save(TodoTestHelper.createTodoEntity("delete this todo", "just delete it"));

        // act
        assertThat(mockMvcTester.delete().uri("/api/todos/" + savedTodo.getId()))
                // assert
                .hasStatus(204);

        assertThat(todoRepository.existsById(savedTodo.getId())).isFalse();
    }
}