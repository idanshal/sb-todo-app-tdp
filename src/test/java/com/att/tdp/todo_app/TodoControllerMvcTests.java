package com.att.tdp.todo_app;

import com.att.tdp.todo_app.controllers.TodoController;
import com.att.tdp.todo_app.dal.TodoRepository;
import com.att.tdp.todo_app.dto.TodoEntity;
import com.att.tdp.todo_app.dto.TodoRequest;
import com.att.tdp.todo_app.services.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class) // used to focus only on the TodoController and not load the full application context.
class TodoControllerMvcTests {

    @Autowired
    private MockMvc mockMvc; // MockMvc is used to perform HTTP requests and verify responses.

    @MockBean // @MockBean is used to create and inject a mock instance of TodoService into the TodoController.
    private TodoService todoService;

    @Test
    void testGetTodos() throws Exception {
        when(todoService.getTodos()).thenReturn(List.of(new TodoEntity()));
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetTodo() throws Exception {
        TodoEntity todo = new TodoEntity();
        todo.setId(1L);
        when(todoService.getTodo(1L)).thenReturn(todo);

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testCreateTodo() throws Exception {
        TodoRequest request = new TodoRequest();
        request.setTitle("dummy");
        request.setDescription("dummy");

        TodoEntity todo = new TodoEntity();
        todo.setId(1L);
        todo.setTitle("Test");
        todo.setDescription("Test Description");

        when(todoService.createTodo(request)).thenReturn(todo);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test\",\"description\":\"Test Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateTodo() throws Exception {
        TodoRequest request = new TodoRequest();
        request.setTitle("Updated Title");

        TodoEntity todo = new TodoEntity();
        todo.setId(1L);
        todo.setTitle("Updated Title");

        when(todoService.updateTodo(eq(1L), any(TodoRequest.class))).thenReturn(todo);

        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Title\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void testDeleteTodo() throws Exception {
        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());
    }
}