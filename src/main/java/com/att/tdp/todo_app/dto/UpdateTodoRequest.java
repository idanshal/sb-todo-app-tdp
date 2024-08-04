package com.att.tdp.todo_app.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UpdateTodoRequest {
    @Size(min = 3, max = 100)
    private String title;

    @Size(max = 300)
    private String description;

    private Boolean isCompleted;
}