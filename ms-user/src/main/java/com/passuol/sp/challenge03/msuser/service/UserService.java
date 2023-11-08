package com.passuol.sp.challenge03.msuser.service;

import com.passuol.sp.challenge03.msuser.exception.UserAlreadyCPFExistsException;
import com.passuol.sp.challenge03.msuser.exception.UserAlreadyEmailExistsException;
import com.passuol.sp.challenge03.msuser.exception.UserNotFoundException;
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


    public UserDTO getUserById(Long id){

        User user = repository.findById(id)
        .orElseThrow(UserNotFoundException::new);

        return userDTOMapper.convertInUserDTO(user);
    }

    public UserDTO createNewUser(UserDTO userDTO){

        User userResponseEmail = repository.findByEmail(userDTO.getEmail());
        User userResponseCpf = repository.findByCpf(userDTO.getCpf());

        if(userResponseEmail != null){
            throw new UserAlreadyEmailExistsException();
        }
        if(userResponseCpf != null){
            throw new UserAlreadyCPFExistsException();
        }

        User newUser = userMapper.convertInUser(userDTO);
        User newUserResponse = repository.save(newUser);

        return userDTOMapper.convertInUserDTO(newUserResponse);
    }

    public void updateUser(Long id, UserDTO userDTO){
        User userRequest = repository.findById(id)
        .orElseThrow(UserNotFoundException::new);

        if(userDTO.getFirstName().isBlank()
            || userDTO.getLastName().isBlank()
            || userDTO.getCpf().isBlank()
            || userDTO.getBirthdate().toString().isBlank()
            || userDTO.getEmail().isBlank()){
            throw new UserNotFoundException();
        }else if(userDTO.getPassword() == null){
            userRequest.setFirstName(userDTO.getFirstName());
            userRequest.setLastName(userDTO.getLastName());
            userRequest.setCpf(userDTO.getCpf());
            userRequest.setBirthdate(userDTO.getBirthdate());
            userRequest.setEmail(userDTO.getEmail());

            repository.save(userRequest);
        }
    }

    public void updateUserPassword(Long id, UserDTO password) {

        User userRequest = repository.findById(id)
        .orElseThrow(UserNotFoundException::new);

        if(password.toString().isBlank())throw new NullPointerException();

        userRequest.setPassword(password.getPassword());

        repository.save(userRequest);

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
