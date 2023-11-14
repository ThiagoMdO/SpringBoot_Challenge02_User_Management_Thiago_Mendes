package com.passuol.sp.challenge03.msuser.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class UserNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private final HttpStatus status;

    public UserNotFoundException(){
        super(ErrorCode.NOT_FOUND.name());
        this.errorCode = ErrorCode.NOT_FOUND;
        this.status = HttpStatus.NOT_FOUND;
    }
}
