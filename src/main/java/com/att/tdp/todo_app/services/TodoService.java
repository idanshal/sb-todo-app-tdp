package com.att.tdp.todo_app.services;

import org.springframework.stereotype.Service;

@Service
public class TodoService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
