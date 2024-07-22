package com.att.tdp.todo_app.exceptions;

import com.att.tdp.todo_app.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleTodoNotFoundException(TodoNotFoundException e) {
        ErrorDto errorDto = new ErrorDto("100", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorDto errorDto = new ErrorDto("101", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

}
