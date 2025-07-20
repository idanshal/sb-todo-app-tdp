package com.att.tdp.todo_app.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTodoRequest {
    @Size(min = 3, max = 100)
    private String title;

    @Size(max = 300)
    private String description;

    private Boolean isCompleted;
}