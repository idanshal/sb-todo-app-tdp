package com.att.tdp.todo_app;

import com.att.tdp.todo_app.dal.TodoRepository;
import com.att.tdp.todo_app.dto.TodoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
class TodoControllerTests {

    //TODO!!!
    // add tests for validation, error handling, empty list
    // check assertj core api for more assertions
    // add assertions for db besides API
    // add DataJPATest (+add to readme)

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach // used to run a method before each test method in the class.
    void setup() {
        todoRepository.deleteAll();
    }

    @Test
    void testGetTodos() throws Exception {
        //String actualResponseBody = mvcResult.getResponse().getContentAsString();
        todoRepository.saveAll(List.of(
                TodoTestHelper.createTodoEntity("Do laundry", "Wash clothes"),
                TodoTestHelper.createTodoEntity("Do dishes", "Wash dishes")
        ));
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].title", containsInAnyOrder("Do laundry", "Do dishes")));
    }


    @Test
    void testGetTodo() throws Exception {
        TodoEntity savedTodo = todoRepository.save(TodoTestHelper.createTodoEntity("Learn something new", "Read a book"));
        mockMvc.perform(get("/api/todos/%d".formatted(savedTodo.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedTodo.getId()));
    }

    @Test
    void testCreateTodo() throws Exception {
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"Test","description":"Test Description"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test"));
    }

    @Test
    void testUpdateTodo() throws Exception {
        TodoEntity todo = new TodoEntity();
        todo.setTitle("Old Title");
        todo.setDescription("Some description");
        todoRepository.save(todo);

        mockMvc.perform(put("/api/todos/" + todo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Title\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void testDeleteTodo() throws Exception {
        TodoEntity savedTodo = todoRepository.save(TodoTestHelper.createTodoEntity("delete this todo", "just delete it"));

        mockMvc.perform(delete("/api/todos/" + savedTodo.getId()))
                .andExpect(status().isNoContent());

        assertThat(todoRepository.existsById(savedTodo.getId())).isFalse();
    }
}