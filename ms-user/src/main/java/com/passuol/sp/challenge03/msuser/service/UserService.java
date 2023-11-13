package com.passuol.sp.challenge03.msuser.service;

import com.passuol.sp.challenge03.msuser.enuns.UserRole;
import com.passuol.sp.challenge03.msuser.exception.UserAlreadyCPFExistsException;
import com.passuol.sp.challenge03.msuser.exception.UserAlreadyEmailExistsException;
import com.passuol.sp.challenge03.msuser.exception.UserNotFoundException;
import com.passuol.sp.challenge03.msuser.exception.UserPasswordInvalid;
import com.passuol.sp.challenge03.msuser.model.dto.AuthenticationDTO;
import com.passuol.sp.challenge03.msuser.model.dto.UserDTORequest;
import com.passuol.sp.challenge03.msuser.model.dto.UserDTOResponse;
import com.passuol.sp.challenge03.msuser.model.entity.User;
import com.passuol.sp.challenge03.msuser.repository.UserRepository;
import com.passuol.sp.challenge03.msuser.repository.UserRepositorySecurity;
import com.passuol.sp.challenge03.msuser.service.mapper.UserDTORequestToUserMapper;
import com.passuol.sp.challenge03.msuser.service.mapper.UserToUserDTOResponseMapper;
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

    private final UserDTORequestToUserMapper userDTORequestToUserMapper;

    private final UserToUserDTOResponseMapper userToUserDTOResponseMapper;


    public UserDTOResponse getUserById(Long id){

        User user = repository.findById(id)
        .orElseThrow(UserNotFoundException::new);

        return userToUserDTOResponseMapper.convertUserToUserDTOResponse(user);
    }

    public UserDTOResponse createNewUser(UserDTORequest userDTORequest){

        User userResponseEmail = repository.findByEmail(userDTORequest.getEmail());
        User userResponseCpf = repository.findByCpf(userDTORequest.getCpf());

        if(userResponseEmail != null){
            throw new UserAlreadyEmailExistsException();
        }
        if(userResponseCpf != null){
            throw new UserAlreadyCPFExistsException();
        }

        User newUser = userDTORequestToUserMapper.convertInUser(userDTORequest);

        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTORequest.getPassword());
        newUser.setPassword(encryptedPassword);

        newUser.setRole(UserRole.USER);
        newUser.setActive(true);

        User newUserResponse = repository.save(newUser);

        return userToUserDTOResponseMapper.convertUserToUserDTOResponse(newUserResponse);
    }

    public UserDTOResponse updateUser(Long id, UserDTORequest userDTORequest){

        User userRequest = repository.findById(id)
        .orElseThrow(UserNotFoundException::new);

        User userResponseEmail = repository.findByEmail(userDTORequest.getEmail());
        User userResponseCpf = repository.findByCpf(userDTORequest.getCpf());

        if(userResponseEmail != null && !Objects.equals(userRequest.getEmail(), userResponseEmail.getEmail())){
            throw new UserAlreadyEmailExistsException();
        }
        if(userResponseCpf != null && !Objects.equals(userRequest.getCpf(), userResponseCpf.getCpf())){
            throw new UserAlreadyCPFExistsException();
        }

        if(userDTORequest.getFirstName().isBlank()
            || userDTORequest.getLastName().isBlank()
            || userDTORequest.getCpf().isBlank()
            || userDTORequest.getBirthdate().toString().isBlank()
            || userDTORequest.getEmail().isBlank()){
            throw new UserNotFoundException();
        }
        if(userDTORequest.getPassword() == null){
            userRequest.setFirstName(userDTORequest.getFirstName());
            userRequest.setLastName(userDTORequest.getLastName());
            userRequest.setCpf(userDTORequest.getCpf());
            userRequest.setBirthdate(userDTORequest.getBirthdate());
            userRequest.setEmail(userDTORequest.getEmail());

            User newUser = repository.save(userRequest);
            return userToUserDTOResponseMapper.convertUserToUserDTOResponse(newUser);
        }
        return null;
    }

    public UserDTOResponse updateUserPassword(Long id, UserDTORequest password) {
        if(password.getPassword().isBlank() || password.getPassword().isEmpty()){
            throw new UserPasswordInvalid();
        }
        User userRequest = repository.findById(id)
        .orElseThrow(UserNotFoundException::new);

        String encryptedPassword = new BCryptPasswordEncoder().encode(password.toString());

        userRequest.setPassword(encryptedPassword);

        repository.save(userRequest);

        return null;
    }

    public void testUserIfExist(AuthenticationDTO user){

        User userResponse = repository.findByEmail(user.email());

        if(userResponse == null){
            throw new UserNotFoundException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repositorySecurity.findByEmail(username);
    }
}
