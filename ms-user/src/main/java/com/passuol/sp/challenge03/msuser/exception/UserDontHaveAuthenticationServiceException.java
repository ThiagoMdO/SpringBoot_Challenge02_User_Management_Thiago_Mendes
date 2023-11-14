package com.passuol.sp.challenge03.msuser.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class UserDontHaveAuthenticationServiceException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private final HttpStatus status;

    public UserDontHaveAuthenticationServiceException(){
        super(ErrorCode.BAD_REQUEST.name());
        this.errorCode = ErrorCode.FAIL_AUTHENTICATION;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
