package com.passuol.sp.challenge03.msuser.service.mapper;

import com.passuol.sp.challenge03.msuser.model.dto.UserDTORequest;
import com.passuol.sp.challenge03.msuser.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDTORequestMapper {

    public UserDTORequest convertInUserDTORequest(User user){
        var convertUserInDTO = new UserDTORequest();

        convertUserInDTO.setId(user.getId());
        convertUserInDTO.setFirstName(user.getFirstName());
        convertUserInDTO.setLastName(user.getLastName());
        convertUserInDTO.setCpf(user.getCpf());
        convertUserInDTO.setBirthdate(user.getBirthdate());
        convertUserInDTO.setEmail(user.getEmail());
        convertUserInDTO.setPassword(user.getPassword());
        convertUserInDTO.setActive(user.getActive());
        convertUserInDTO.setRole(user.getRole());

        return convertUserInDTO;
    }
}
