package com.passuol.sp.challenge03.msuser.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class UserPasswordInvalid extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    private final HttpStatus status;

    public UserPasswordInvalid(){
        super(ErrorCode.PASSWORD_INVALID.name());
        this.errorCode = ErrorCode.PASSWORD_INVALID;
        this.status = HttpStatus.BAD_REQUEST;

    }

}
