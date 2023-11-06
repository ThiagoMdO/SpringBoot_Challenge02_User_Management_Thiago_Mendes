package com.passuol.sp.challenge03.msuser.service.mapper;

import com.passuol.sp.challenge03.msuser.model.dto.UserDTO;
import com.passuol.sp.challenge03.msuser.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User convertInUser(UserDTO userDTO){
        var convertDTOInUser = new User();

        convertDTOInUser.setId(userDTO.getId());
        convertDTOInUser.setFirstName(userDTO.getFirstName());
        convertDTOInUser.setLastName(userDTO.getLastName());
        convertDTOInUser.setCpf(userDTO.getCpf());
        convertDTOInUser.setBirthdate(userDTO.getBirthdate());
        convertDTOInUser.setEmail(userDTO.getEmail());
        convertDTOInUser.setPassword(userDTO.getPassword());
        convertDTOInUser.setActive(userDTO.getActive());

        return convertDTOInUser;
    }
}
