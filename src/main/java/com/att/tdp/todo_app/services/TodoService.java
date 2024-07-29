package com.att.tdp.todo_app.services;

import com.att.tdp.todo_app.dal.TodoRepository;
import com.att.tdp.todo_app.dto.TodoEntity;
import com.att.tdp.todo_app.dto.TodoRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoEntity> getTodos() {
        return todoRepository.findAll();
    }

    public Optional<TodoEntity> getTodo(Long id) {
        return todoRepository.findById(id);
    }

    public TodoEntity createTodo(TodoRequest todoRequest) {
        TodoEntity todo = new TodoEntity();
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        todo.setIsCompleted(false);
        return todoRepository.save(todo);
    }

    public TodoEntity updateTodo(Long id, TodoRequest todoRequest) {
        Optional<TodoEntity> todoEntity = todoRepository.findById(id);
        if (todoEntity.isEmpty()) {
            return null;
        }

        TodoEntity updatedTodo = todoEntity.get();
        if (todoRequest.getTitle() != null) {
            updatedTodo.setTitle(todoRequest.getTitle());
        }

        if (todoRequest.getDescription() != null) {
            updatedTodo.setDescription(todoRequest.getDescription());
        }

        if (todoRequest.getIsCompleted() != null) {
            updatedTodo.setIsCompleted(todoRequest.getIsCompleted());
        }

        todoRepository.save(updatedTodo);
        return updatedTodo;
    }

    public Long deleteTodo(Long id) {
        if (todoRepository.findById(id).isPresent()) {
            todoRepository.deleteById(id);
            return id;
        }

        return null;
    }
}
