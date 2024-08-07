package com.att.tdp.todo_app;

import com.att.tdp.todo_app.controllers.TodoController;
import com.att.tdp.todo_app.dto.TodoEntity;
import com.att.tdp.todo_app.dto.CreateTodoRequest;
import com.att.tdp.todo_app.dto.UpdateTodoRequest;
import com.att.tdp.todo_app.exceptions.RestExceptionHandler;
import com.att.tdp.todo_app.exceptions.TodoNotFoundException;
import com.att.tdp.todo_app.services.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class) // used to focus only on the TodoController and not load the full application context.
class TodoControllerMvcTests {

    @Autowired
    private MockMvc mockMvc; // MockMvc is used to perform HTTP requests and verify responses.

    @MockBean // @MockBean is used to create and inject a mock instance of TodoService into the TodoController.
    private TodoService todoService;

    @Test
    void testEmptyTodosSuccess() throws Exception {
        // arrange
        when(todoService.getTodos()).thenReturn(Collections.emptyList());
        // act
        mockMvc.perform(get("/api/todos"))
                // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @SneakyThrows
    void testGetTodoNegativeIdValidationFailure() {
        // act
        mockMvc.perform(get("/api/todos/-1"))
                // assert
                .andExpect(status().is4xxClientError());
    }

    @Test
    @SneakyThrows
    void testCreateTodoBlankTitleValidationFailure() {
        // arrange
        CreateTodoRequest request = new CreateTodoRequest();
        request.setTitle("");
        request.setDescription("dummy");

        TodoEntity todo = new TodoEntity();
        todo.setId(1L);
        todo.setTitle("Test");
        todo.setDescription("Test Description");

        when(todoService.createTodo(request)).thenReturn(todo);

        // act
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                // assert
                .andExpect(status().isBadRequest());
        //.andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetTodoNotFoundFailure() throws Exception {
        // arrange
        when(todoService.getTodo(1L)).thenThrow(new TodoNotFoundException("Todo not found"));
        // act
        mockMvc.perform(get("/api/todos/1"))
                // assert
                .andExpect(status().isNotFound());
    }
}