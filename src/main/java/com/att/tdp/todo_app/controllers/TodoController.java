package com.att.tdp.todo_app.controllers;

import com.att.tdp.todo_app.dto.TodoEntity;
import com.att.tdp.todo_app.dto.CreateTodoRequest;
import com.att.tdp.todo_app.dto.UpdateTodoRequest;
import com.att.tdp.todo_app.services.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<TodoEntity> getTodo(@PathVariable Long id) {
        Optional<TodoEntity> todo = todoService.getTodo(id);
        return todo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TodoEntity> createTodo(@RequestBody CreateTodoRequest createTodoRequest) {
        TodoEntity todo = todoService.createTodo(createTodoRequest);
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoEntity> updateTodo(@PathVariable Long id, @RequestBody UpdateTodoRequest updateTodoRequest) {
        TodoEntity updatedTodo = todoService.updateTodo(id, updateTodoRequest);
        if (updatedTodo != null) {
            return ResponseEntity.ok(updatedTodo);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        Long deletedTodoId = todoService.deleteTodo(id);
        if (deletedTodoId != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
