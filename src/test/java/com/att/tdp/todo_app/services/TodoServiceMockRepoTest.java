package com.att.tdp.todo_app.services;

import com.att.tdp.todo_app.dal.TodoRepository;
import com.att.tdp.todo_app.dto.CreateTodoRequest;
import com.att.tdp.todo_app.dto.TodoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TodoServiceMockRepoTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void testCreateTodo() {
        // arrange
        CreateTodoRequest request = new CreateTodoRequest();
        request.setTitle("Test");
        request.setDescription("Test Description");
        // act
        todoService.createTodo(request);
        // assert
        ArgumentCaptor<TodoEntity> captor = ArgumentCaptor.forClass(TodoEntity.class);
        verify(todoRepository).save(captor.capture());
        TodoEntity capturedTodo = captor.getValue();
        assertThat(capturedTodo.getTitle()).isEqualTo("Test");
        assertThat(capturedTodo.getDescription()).isEqualTo("Test Description");
        assertThat(capturedTodo.isCompleted()).isFalse();
    }
}
