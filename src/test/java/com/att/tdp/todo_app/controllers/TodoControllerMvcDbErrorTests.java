package com.att.tdp.todo_app.controllers;

import com.att.tdp.todo_app.dal.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerMvcDbErrorTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoRepository todoRepository;

    @Test
    void testDbError() throws Exception {
        // arrange
        String dbExceptionMessage = "DB Error";
        when(todoRepository.findAll()).thenThrow(new DataAccessException(dbExceptionMessage) {});
        // act
        mockMvc.perform(get("/api/todos"))
                // assert
                .andExpect(status().is5xxServerError())
                .andExpect(content().json("""
                        {"errorCode":"103","errorMessage":"%s"}""".formatted(dbExceptionMessage)));
    }
}