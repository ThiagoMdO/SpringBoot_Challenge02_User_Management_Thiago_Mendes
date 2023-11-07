package com.passuol.sp.challenge03.msuser.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ApiExceptionHandller {
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleIOException(){
        var problem = new Problem(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    private ResponseEntity<Object> handlerMethodArgumentNotValidException(EmptyResultDataAccessException ex){
        var problem = new Problem(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleBadRequest() {
        var problem = new Problem(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handlerAllExceptions(){
        var problem = new Problem(ErrorCode.SYSTEM_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }

    @ExceptionHandler(UserAlreadyEmailExistsException.class)
    public final ResponseEntity<Object> handlerUserAlreadyEmailExists(UserAlreadyEmailExistsException exception){
        var problem = new Problem(exception.getErrorCode(), exception.getStatus());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }

    @ExceptionHandler(UserAlreadyCPFExistsException.class)
    public final ResponseEntity<Object> handlerUserAlreadyCPFExists(UserAlreadyCPFExistsException exception){
        var problem = new Problem(exception.getErrorCode(), exception.getStatus());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handlerUserNotFound(UserNotFoundException exception){
        var problem = new Problem(exception.getErrorCode(), exception.getStatus());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
}
