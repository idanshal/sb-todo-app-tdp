package com.att.tdp.todo_app.controller;

import com.att.tdp.todo_app.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(TodoController.class)
class TodoControllerMvcDbErrorTests {

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean
    private TodoService todoService;

    @Test
    void testDbError() {
        // arrange
        String dbExceptionMessage = "DB Error";
        when(todoService.getTodos()).thenThrow(new DataAccessException(dbExceptionMessage) {});
        // act
        assertThat(mockMvcTester.get().uri("/api/todos"))
                // assert
            .hasStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .bodyJson()
            .isLenientlyEqualTo("""
                {"errorCode":"103","errorMessage":"%s"}""".formatted(dbExceptionMessage));
    }
}