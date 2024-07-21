package com.att.tdp.todo_app.controllers;

import com.att.tdp.todo_app.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/hello")
    public String hello(@PathVariable String name) {
        return todoService.sayHello(name);
    }
}
