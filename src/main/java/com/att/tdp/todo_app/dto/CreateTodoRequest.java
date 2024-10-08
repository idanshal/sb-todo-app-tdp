package com.att.tdp.todo_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTodoRequest {
    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @NotBlank
    @Size(max = 300)
    private String description;
}