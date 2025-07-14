package com.att.tdp.todo_app.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.att.tdp.todo_app.dal.TodoRepository;
import com.att.tdp.todo_app.dto.CreateTodoRequest;
import com.att.tdp.todo_app.dto.TodoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class TodoServiceRealRepoTest {

    @Autowired
    private TodoRepository todoRepository;

    private TodoService todoService;

    @Test
    void testCreateTodo() {
        // Initialize the service with the real repository
        todoService = new TodoService(todoRepository);

        // arrange
        CreateTodoRequest request = new CreateTodoRequest();
        request.setTitle("Test");
        request.setDescription("Test Description");

        // act
        todoService.createTodo(request);

        // assert
        var todos = todoRepository.findAll();
        assertThat(todos).hasSize(1);

        TodoEntity savedTodo = todos.getFirst();
        assertThat(savedTodo.getId()).isNotNull();
        assertThat(savedTodo.getTitle()).isEqualTo("Test");
        assertThat(savedTodo.getDescription()).isEqualTo("Test Description");
        assertThat(savedTodo.isCompleted()).isFalse();
    }
}