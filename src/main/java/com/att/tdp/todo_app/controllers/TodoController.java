package com.att.tdp.todo_app.controllers;

import com.att.tdp.todo_app.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello(@RequestParam String name) {
        String result = todoService.sayHello(name);
        return ResponseEntity.ok(result);
    }
}
