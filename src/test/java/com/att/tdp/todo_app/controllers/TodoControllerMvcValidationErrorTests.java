package com.att.tdp.todo_app.controllers;

import com.att.tdp.todo_app.dto.CreateTodoRequest;
import com.att.tdp.todo_app.exceptions.TodoNotFoundException;
import com.att.tdp.todo_app.services.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class) // used to focus only on the TodoController and not load the full application context.
class TodoControllerMvcValidationErrorTests {

    @Autowired
    private MockMvc mockMvc; // MockMvc is used to perform HTTP requests and verify responses.

    @MockitoBean // @MockBean is used to create and inject a mock instance of TodoService into the TodoController.
    private TodoService todoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

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

        // act
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // assert
                .andExpect(status().isBadRequest());
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