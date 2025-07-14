package com.att.tdp.todo_app.controllers;

import com.att.tdp.todo_app.dto.TodoEntity;
import com.att.tdp.todo_app.dto.CreateTodoRequest;
import com.att.tdp.todo_app.dto.UpdateTodoRequest;
import com.att.tdp.todo_app.services.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoEntity> getTodos() {
        return todoService.getTodos();
    }

    @GetMapping("illegal")
    public void simulateIllegalArgumentException() {
        throw new IllegalArgumentException("illegal");
    }

    @GetMapping("/{id}")
    public TodoEntity getTodo(@PathVariable Long id) {
        return todoService.getTodo(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoEntity createTodo(@RequestBody CreateTodoRequest createTodoRequest) {
        return todoService.createTodo(createTodoRequest);
    }

    @PutMapping("/{id}")
    public TodoEntity updateTodo(@PathVariable Long id, @RequestBody UpdateTodoRequest updateTodoRequest) {
        return todoService.updateTodo(id, updateTodoRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
    }
}
