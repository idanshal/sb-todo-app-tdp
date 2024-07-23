package com.att.tdp.todo_app.exceptions;

import com.att.tdp.todo_app.dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.util.stream.Collectors.joining;

//@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleTodoNotFoundException(TodoNotFoundException e) {
        return new ErrorDto("100", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorDto("101", e.getMessage());
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers,
//                                                                  HttpStatusCode status,
//                                                                  WebRequest request) {
//        logger.error("#handleMethodArgumentNotValid - ", ex);
//        String message = buildMessage(ex);
//        ErrorDto errorDto = new ErrorDto("102", message);
//        return new ResponseEntity<>(errorDto, status);
//    }

    private String buildMessage(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return bindingResult.getFieldErrors()
                .stream()
                .map(this::toMessage)
                .collect(joining(", "));
    }

    private String toMessage(FieldError error) {
        return String.format("Field '%s.%s' %s", error.getObjectName(), error.getField(), error.getDefaultMessage());
    }

}
