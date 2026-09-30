package com.att.tdp.todo_app.controller;

import com.att.tdp.todo_app.exception.TodoNotFoundException;
import com.att.tdp.todo_app.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.stream.Stream;

@WebMvcTest(TodoController.class) // used to focus only on the TodoController and not load the full application context.
class TodoControllerMvcValidationErrorTests {

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean
    private TodoService todoService;

    @Test
    void testGetTodoNegativeIdValidationFailure() {
        // act
        assertThat(mockMvcTester.get().uri("/api/todos/-1"))
                // assert
            .hasStatus(HttpStatus.BAD_REQUEST)
            .bodyJson()
            .extractingPath("$.errorCode")
            .asString()
            .isEqualTo("102");

        verifyNoInteractions(todoService);
    }

    @ParameterizedTest
    @MethodSource("invalidCreateRequests")
    void testCreateTodoValidationFailure(String requestBody, String invalidField) {
        // act
        assertThat(mockMvcTester.post().uri("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                // assert
            .hasStatus(HttpStatus.BAD_REQUEST)
            .bodyJson()
            .extractingPath("$.errorMessage")
            .asString()
            .contains(invalidField);

        verifyNoInteractions(todoService);
    }

    static Stream<Arguments> invalidCreateRequests() {
        return Stream.of(
                Arguments.of("{\"title\":\"\",\"description\":\"Valid description\"}", "title"),
                Arguments.of("{\"title\":\"ab\",\"description\":\"Valid description\"}", "title"),
                Arguments.of("{\"title\":\"%s\",\"description\":\"Valid description\"}".formatted("x".repeat(101)), "title"),
                Arguments.of("{\"title\":\"Valid title\",\"description\":\"\"}", "description"),
                Arguments.of("{\"title\":\"Valid title\",\"description\":\"%s\"}".formatted("x".repeat(301)), "description")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidUpdateRequests")
    void testUpdateTodoValidationFailure(String requestBody, String invalidField) {
        assertThat(mockMvcTester.put().uri("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .extractingPath("$.errorMessage")
                .asString()
                .contains(invalidField);

        verifyNoInteractions(todoService);
    }

    static Stream<Arguments> invalidUpdateRequests() {
        return Stream.of(
                Arguments.of("{\"title\":\"ab\"}", "title"),
                Arguments.of("{\"description\":\"%s\"}".formatted("x".repeat(301)), "description")
        );
    }

    @Test
    void testGetTodoNotFoundFailure() {
        // arrange
        when(todoService.getTodo(1L)).thenThrow(new TodoNotFoundException("Todo not found"));
        // act
        assertThat(mockMvcTester.get().uri("/api/todos/1"))
                // assert
            .hasStatus(HttpStatus.NOT_FOUND)
            .bodyJson()
            .isLenientlyEqualTo("""
                {"errorCode":"100","errorMessage":"Todo not found"}
                """);
    }

    @Test
    void testIllegalArgumentFailure() {
        assertThat(mockMvcTester.get().uri("/api/todos/illegal"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .isLenientlyEqualTo("""
                        {"errorCode":"101","errorMessage":"illegal"}
                        """);

        verifyNoInteractions(todoService);
    }
}