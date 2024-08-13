package com.att.tdp.todo_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {
    private String errorCode;
    private String errorMessage;
}
