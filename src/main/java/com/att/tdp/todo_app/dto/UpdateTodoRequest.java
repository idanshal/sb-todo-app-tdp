package com.att.tdp.todo_app.dto;

import jakarta.validation.constraints.Size;

public class UpdateTodoRequest {

    @Size(min = 3, max = 100)
    private String title;

    @Size(max = 300)
    private String description;

    private Boolean isCompleted;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}