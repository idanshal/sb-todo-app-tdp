package com.att.tdp.todo_app.controllers;

import com.att.tdp.todo_app.dto.ErrorDto;
import com.att.tdp.todo_app.dto.TodoEntity;
import com.att.tdp.todo_app.dto.TodoRequest;
import com.att.tdp.todo_app.exceptions.TodoNotFoundException;
import com.att.tdp.todo_app.services.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<TodoEntity>> getTodos() {
        List<TodoEntity> todos = todoService.getTodos();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("illegal")
    public ResponseEntity<Void> simulateIllegalArgumentException() {
        throw new IllegalArgumentException("illegal");
    }

//    @ExceptionHandler
//    public ResponseEntity<ErrorDto> handleTodoNotFoundException(TodoNotFoundException e) {
//        ErrorDto errorDto = new ErrorDto("100", e.getMessage());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoEntity> getTodo(@PathVariable Long id) {
        TodoEntity todo = todoService.getTodo(id);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<TodoEntity> createTodo(@RequestBody TodoRequest todoRequest) {
        TodoEntity todo = todoService.createTodo(todoRequest);
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoEntity> updateTodo(@PathVariable Long id, @RequestBody TodoRequest todoRequest) {
        TodoEntity updatedTodo = todoService.updateTodo(id, todoRequest);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
