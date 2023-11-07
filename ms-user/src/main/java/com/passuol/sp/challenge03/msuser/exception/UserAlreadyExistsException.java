package com.passuol.sp.challenge03.msuser.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class UserAlreadyExistsException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private final HttpStatus status;

    public UserAlreadyExistsException() {
        super(ErrorCode.CONFLICT.name());
        this.status = HttpStatus.CONFLICT;
        this.errorCode = ErrorCode.CONFLICT;
    }
}
