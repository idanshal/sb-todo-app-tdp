package com.att.tdp.todo_app.dto;

import lombok.Value;

@Value
public class ErrorDto {
    String errorCode;
    String errorMessage;
}
