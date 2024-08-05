package com.att.tdp.todo_app.services;

import com.att.tdp.todo_app.dal.TodoRepository;
import com.att.tdp.todo_app.dto.TodoEntity;
import com.att.tdp.todo_app.dto.CreateTodoRequest;
import com.att.tdp.todo_app.dto.UpdateTodoRequest;
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

    public TodoEntity createTodo(CreateTodoRequest createTodoRequest) {
        TodoEntity todo = new TodoEntity();
        todo.setTitle(createTodoRequest.getTitle());
        todo.setDescription(createTodoRequest.getDescription());
        todo.setIsCompleted(false);
        return todoRepository.save(todo);
    }

    public TodoEntity updateTodo(Long id, UpdateTodoRequest updateTodoRequest) {
        Optional<TodoEntity> todoEntity = todoRepository.findById(id);
        if (todoEntity.isEmpty()) {
            return null;
        }

        TodoEntity updatedTodo = todoEntity.get();
        if (updateTodoRequest.getTitle() != null) {
            updatedTodo.setTitle(updateTodoRequest.getTitle());
        }

        if (updateTodoRequest.getDescription() != null) {
            updatedTodo.setDescription(updateTodoRequest.getDescription());
        }

        if (updateTodoRequest.getIsCompleted() != null) {
            updatedTodo.setIsCompleted(updateTodoRequest.getIsCompleted());
        }

        todoRepository.save(updatedTodo);
        return updatedTodo;
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
