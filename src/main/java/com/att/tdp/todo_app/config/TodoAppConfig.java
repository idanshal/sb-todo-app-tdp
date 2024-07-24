package com.att.tdp.todo_app.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "todo-app")
@Data
@NoArgsConstructor
public class TodoAppConfig {
    private String version;
    private String title;
    private String description;
}
