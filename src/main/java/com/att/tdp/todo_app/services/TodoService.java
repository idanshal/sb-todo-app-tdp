package com.att.tdp.todo_app.services;

import com.att.tdp.todo_app.dal.TodoRepository;
import com.att.tdp.todo_app.dto.TodoEntity;
import com.att.tdp.todo_app.dto.CreateTodoRequest;
import com.att.tdp.todo_app.dto.UpdateTodoRequest;
import com.att.tdp.todo_app.exceptions.TodoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoEntity> getTodos() {
        return todoRepository.findAll();
    }

    public TodoEntity getTodo(Long id) {
        Optional<TodoEntity> todo = todoRepository.findById(id);
        if (todo.isEmpty()) {
            throw new TodoNotFoundException("todo not found. id: %s".formatted(id));
        }
        return todo.get();
    }

    public TodoEntity createTodo(CreateTodoRequest createTodoRequest) {
        TodoEntity todo = new TodoEntity();
        todo.setTitle(createTodoRequest.getTitle());
        todo.setDescription(createTodoRequest.getDescription());
        todo.setCompleted(false);
        return todoRepository.save(todo);
    }

    public TodoEntity updateTodo(Long id, UpdateTodoRequest updateTodoRequest) {
        TodoEntity updatedTodo = getTodo(id);
        if (updateTodoRequest.getTitle() != null) {
            updatedTodo.setTitle(updateTodoRequest.getTitle());
        }

        if (updateTodoRequest.getDescription() != null) {
            updatedTodo.setDescription(updateTodoRequest.getDescription());
        }

        if (updateTodoRequest.getIsCompleted() != null) {
            updatedTodo.setCompleted(updateTodoRequest.getIsCompleted());
        }

        todoRepository.save(updatedTodo);
        return updatedTodo;
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
