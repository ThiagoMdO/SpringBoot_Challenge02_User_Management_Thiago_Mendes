package com.passuol.sp.challenge03.msuser.service.mapper;

import com.passuol.sp.challenge03.msuser.model.dto.UserDTOResponse;
import com.passuol.sp.challenge03.msuser.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDTOResponseMapper {
    public UserDTOResponse convertUserToUserDTOResponse(User user){

        var convertUserToUserDTOResponse = new UserDTOResponse();

        convertUserToUserDTOResponse.setId(user.getId());
        convertUserToUserDTOResponse.setFirstName(user.getFirstName());
        convertUserToUserDTOResponse.setLastName(user.getLastName());
        convertUserToUserDTOResponse.setCpf(user.getCpf());
        convertUserToUserDTOResponse.setBirthdate(user.getBirthdate());
        convertUserToUserDTOResponse.setEmail(user.getEmail());
//        convertUserToUserDTOResponse.setPassword(user.getPassword());
//        convertUserToUserDTOResponse.setActive(user.getActive());
//        convertUserToUserDTOResponse.setRole(user.getRole());

        return convertUserToUserDTOResponse;
    }
}
