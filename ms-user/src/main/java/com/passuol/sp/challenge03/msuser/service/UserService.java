package com.passuol.sp.challenge03.msuser.service;

import com.passuol.sp.challenge03.msuser.exception.UserAlreadyCPFExistsException;
import com.passuol.sp.challenge03.msuser.exception.UserAlreadyEmailExistsException;
import com.passuol.sp.challenge03.msuser.exception.UserNotFoundException;
import com.passuol.sp.challenge03.msuser.model.dto.UserDTO;
import com.passuol.sp.challenge03.msuser.model.entity.User;
import com.passuol.sp.challenge03.msuser.repository.UserRepository;
import com.passuol.sp.challenge03.msuser.repository.UserRepositorySecurity;
import com.passuol.sp.challenge03.msuser.service.mapper.UserDTOMapper;
import com.passuol.sp.challenge03.msuser.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    private final UserRepositorySecurity repositorySecurity;

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

        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        newUser.setPassword(encryptedPassword);

        User newUserResponse = repository.save(newUser);

        return userDTOMapper.convertInUserDTO(newUserResponse);
    }

    public void updateUser(Long id, UserDTO userDTO){
        User userRequest = repository.findById(id)
        .orElseThrow(UserNotFoundException::new);

        User userResponseEmail = repository.findByEmail(userDTO.getEmail());
        User userResponseCpf = repository.findByCpf(userDTO.getCpf());

        if(userResponseEmail != null && !Objects.equals(userRequest.getEmail(), userResponseEmail.getEmail())){
            throw new UserAlreadyEmailExistsException();
        }
        if(userResponseCpf != null && !Objects.equals(userRequest.getCpf(), userResponseCpf.getCpf())){
            throw new UserAlreadyCPFExistsException();
        }

        if(userDTO.getFirstName().isBlank()
            || userDTO.getLastName().isBlank()
            || userDTO.getCpf().isBlank()
            || userDTO.getBirthdate().toString().isBlank()
            || userDTO.getEmail().isBlank()){
            throw new UserNotFoundException();
        }
        if(userDTO.getPassword() == null){
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

        String encryptedPassword = new BCryptPasswordEncoder().encode(password.toString());

        userRequest.setPassword(encryptedPassword);

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repositorySecurity.findByEmail(username);
    }
}
