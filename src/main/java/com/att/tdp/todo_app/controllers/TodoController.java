package com.att.tdp.todo_app.controllers;

import com.att.tdp.todo_app.dto.TodoEntity;
import com.att.tdp.todo_app.dto.TodoRequest;
import com.att.tdp.todo_app.services.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<TodoEntity> getTodos() {
        return todoService.getTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoEntity> getTodo(@PathVariable Long id) {
        Optional<TodoEntity> todo = todoService.getTodo(id);
        return todo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TodoEntity createTodo(@RequestBody TodoRequest todo) {
        return todoService.createTodo(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoEntity> updateTodo(@PathVariable Long id, @RequestBody TodoRequest todoRequest) {
        TodoEntity updatedTodo = todoService.updateTodo(id, todoRequest);
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
