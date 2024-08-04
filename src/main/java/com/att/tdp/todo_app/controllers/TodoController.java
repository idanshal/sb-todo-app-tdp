package com.att.tdp.todo_app.controllers;

import com.att.tdp.todo_app.dto.TodoEntity;
import com.att.tdp.todo_app.dto.CreateTodoRequest;
import com.att.tdp.todo_app.services.TodoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@Validated
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
    public TodoEntity getTodo(@PathVariable @Positive Long id) {
        return todoService.getTodo(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoEntity createTodo(@RequestBody @Valid CreateTodoRequest createTodoRequest) {
        return todoService.createTodo(createTodoRequest);
    }

    @PutMapping("/{id}")
    public TodoEntity updateTodo(@PathVariable @Positive Long id, @RequestBody @Valid CreateTodoRequest todoRequest) {
        return todoService.updateTodo(id, todoRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable @Positive Long id) {
        todoService.deleteTodo(id);
    }
}
