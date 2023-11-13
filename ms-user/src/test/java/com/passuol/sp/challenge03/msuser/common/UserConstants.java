package com.passuol.sp.challenge03.msuser.common;

import com.passuol.sp.challenge03.msuser.model.dto.UserDTORequest;
import com.passuol.sp.challenge03.msuser.model.dto.UserDTOResponse;
import com.passuol.sp.challenge03.msuser.model.entity.User;

public class UserConstants {

    public static final User USER = new User(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);

    public static final UserDTORequest USER_DTO_REQUEST = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);

    public static final UserDTOResponse USER_DTO_RESPONSE = new UserDTOResponse( "firstName", "lastName", "123.456.721-31","15/01/1990", "maria@example.com");
}
