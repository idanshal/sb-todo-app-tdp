package com.att.tdp.todo_app;

import com.att.tdp.todo_app.dal.TodoRepository;
import com.att.tdp.todo_app.dto.TodoEntity;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // used for loading the full application context for integration tests.
@AutoConfigureMockMvc // used for configuring MockMvc for testing web layer components in a Spring Boot application
class TodoControllerHappyTests {
    // add DataJPATest (+add to readme)

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
        // used to run a method before each test method in the class.
    void setup() {
        todoRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void testGetTodosSuccess() {
        //arrange
        todoRepository.saveAll(List.of(
                TodoTestHelper.createTodoEntity("Do laundry", "Wash clothes"),
                TodoTestHelper.createTodoEntity("Do dishes", "Wash dishes")
        ));

        //act
        mockMvc.perform(get("/api/todos"))
                //assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].title", containsInAnyOrder("Do laundry", "Do dishes")));
    }

    @Test
    void testEmptyTodosSuccess() throws Exception {
        // act
        mockMvc.perform(get("/api/todos"))
                // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @SneakyThrows
    void testGetTodoSuccess() {
        // arrange
        TodoEntity savedTodo = todoRepository.save(TodoTestHelper.createTodoEntity("Learn something new", "Read a book"));
        // act
        mockMvc.perform(get("/api/todos/%d".formatted(savedTodo.getId())))
                // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedTodo.getId()));
    }

    @Test
    @SneakyThrows
    void testCreateTodoSuccess() {
        // act
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Test","description":"Test Description"}
                                """))
                // assert
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test"));

        assertThat(todoRepository.findAll()).hasSize(1);
    }

    @Test
    void testUpdateTodo() throws Exception {

        // arrange
        TodoEntity todo = new TodoEntity();
        todo.setTitle("Old Title");
        todo.setDescription("Some description");
        todoRepository.save(todo);

        // act
        mockMvc.perform(put("/api/todos/" + todo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Title\"}"))
                // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));

        assertThat(todoRepository.findById(todo.getId()).get().getTitle()).isEqualTo("Updated Title");
    }

    @Test
    void testDeleteTodoSuccess() throws Exception {
        // arrange
        TodoEntity savedTodo = todoRepository.save(TodoTestHelper.createTodoEntity("delete this todo", "just delete it"));

        // act
        mockMvc.perform(delete("/api/todos/" + savedTodo.getId()))
                // assert
                .andExpect(status().isNoContent());

        assertThat(todoRepository.existsById(savedTodo.getId())).isFalse();
    }
}