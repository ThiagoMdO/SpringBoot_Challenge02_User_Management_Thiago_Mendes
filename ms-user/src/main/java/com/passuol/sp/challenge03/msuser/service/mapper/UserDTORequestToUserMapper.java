package com.passuol.sp.challenge03.msuser.service.mapper;

import com.passuol.sp.challenge03.msuser.model.dto.UserDTORequest;
import com.passuol.sp.challenge03.msuser.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTORequestToUserMapper {

    public User convertInUser(UserDTORequest userDTORequest){

        var convertDTOInUser = new User();

        convertDTOInUser.setId(userDTORequest.getId());
        convertDTOInUser.setFirstName(userDTORequest.getFirstName());
        convertDTOInUser.setLastName(userDTORequest.getLastName());
        convertDTOInUser.setCpf(userDTORequest.getCpf());
        convertDTOInUser.setBirthdate(userDTORequest.getBirthdate());
        convertDTOInUser.setEmail(userDTORequest.getEmail());
        convertDTOInUser.setPassword(userDTORequest.getPassword());
        convertDTOInUser.setActive(userDTORequest.getActive());
        convertDTOInUser.setRole(userDTORequest.getRole());

        return convertDTOInUser;
    }
}
