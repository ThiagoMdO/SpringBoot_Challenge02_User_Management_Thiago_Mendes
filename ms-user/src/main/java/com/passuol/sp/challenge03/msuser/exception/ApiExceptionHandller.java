package com.passuol.sp.challenge03.msuser.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
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
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(){
        var problem = new Problem(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handlleConstraintViolationException(){
        var problem = new Problem(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(){
        var problem = new Problem(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }
//    @ExceptionHandler(DateTimeParseException.class)
//    public ResponseEntity<Object> handleDateTimeParseException(){
//        var problem = new Problem(ErrorCode.DATE_FORMAT_INCOMPATIBLE, HttpStatus.BAD_REQUEST);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
//    }

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
    @ExceptionHandler(TransactionSystemException.class)
    public final ResponseEntity<Object> handleTransactionSystemException() {
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
    @ExceptionHandler(UserDontHaveAuthenticationServiceException.class)
    public final ResponseEntity<Object> handlerUserDontHaveAuthenticationServiceException(UserDontHaveAuthenticationServiceException exception){
        var problem = new Problem(exception.getErrorCode(), exception.getStatus());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }
    @ExceptionHandler(UserPasswordInvalid.class)
    public final ResponseEntity<Object> handlerUserPasswordInvalid(UserPasswordInvalid exception){
        var problem = new Problem(exception.getErrorCode(), exception.getStatus());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }
}
