package com.passuol.sp.challenge03.msuser.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND("Data Not Found"),

    BAD_REQUEST("Invalid Data"),

    CONFLICT_EMAIL("Conflict between the fields, this Email Address is already in using"),

    CONFLICT_CPF("Conflict between the fields, this CPF number is already in using"),

    FAIL_AUTHENTICATION("You don't have a valid account"),

    SYSTEM_ERROR("Unavailable Server");

    private final String message;
}
