package com.att.tdp.todo_app.controllers;

import com.att.tdp.todo_app.config.TodoAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/meta")
public class MetaController {
    @Autowired
    private TodoAppConfig todoAppConfig;

    @Value("${todo-app.version}")
    private String version;

    @Value("${server.port}")
    private int serverPort;

    @Value("${PATH}")
    private String path;

    @Autowired
    private Environment environment;

    @GetMapping
    public TodoAppConfig getMeta() {
        return todoAppConfig;
    }

    @GetMapping("/version")
    public String getMetaVersion() {
        return version;
    }

    @GetMapping("/port")
    public int getMetaPort() {
        return serverPort;
    }

    @GetMapping("/java-home")
    public String getMetaJavaHomePath() {
        return environment.getProperty("JAVA_HOME");
    }

    @GetMapping("/path")
    public String getMetaPath() {
        return path;
    }

}
