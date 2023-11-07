package com.passuol.sp.challenge03.msuser.service;

import com.passuol.sp.challenge03.msuser.exception.UserAlreadyExistsException;
import com.passuol.sp.challenge03.msuser.model.dto.UserDTO;
import com.passuol.sp.challenge03.msuser.model.entity.User;
import com.passuol.sp.challenge03.msuser.repository.UserRepository;
import com.passuol.sp.challenge03.msuser.service.mapper.UserDTOMapper;
import com.passuol.sp.challenge03.msuser.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final UserMapper userMapper;

    private final UserDTOMapper userDTOMapper;


    public UserDTO createNewUser(UserDTO userDTO){

        User userResponseEmail = repository.findByEmail(userDTO.getEmail());
        User userResponseCpf = repository.findByCpf(userDTO.getCpf());
        if(userResponseEmail != null || userResponseCpf != null){
            throw new UserAlreadyExistsException();
        }

        User newUser = userMapper.convertInUser(userDTO);
        User newUserResponse = repository.save(newUser);
        return userDTOMapper.convertInUserDTO(newUserResponse);

    }

//    public UserDTO showUserWithExist(UserDTO userDTO){
//
//        User userResponse = repository.findByEmail(userDTO.getEmail());
//        if(userResponse != null){
//            return userDTOMapper.convertInUserDTO(userResponse);
//        }
//        //String emailResponse = String.valueOf();
//
//        return null;
//    }
}
