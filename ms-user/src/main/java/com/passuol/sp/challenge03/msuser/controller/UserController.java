package com.passuol.sp.challenge03.msuser.controller;

import com.passuol.sp.challenge03.msuser.infra.security.TokenService;
import com.passuol.sp.challenge03.msuser.model.dto.AuthenticationDTO;
import com.passuol.sp.challenge03.msuser.model.dto.UserDTORequest;
import com.passuol.sp.challenge03.msuser.model.dto.UserDTOResponse;
import com.passuol.sp.challenge03.msuser.model.dto.UserResponseTokenDTO;
import com.passuol.sp.challenge03.msuser.model.entity.User;
import com.passuol.sp.challenge03.msuser.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class UserController {

    private final UserService userService;

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTOResponse> find(@PathVariable("id") Long id){
        UserDTOResponse userDTORequest = userService.getUserById(id);
        return ResponseEntity.ok().body(userDTORequest);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTOResponse> create(@RequestBody @Valid UserDTORequest userDTORequest){
        UserDTOResponse newUser = userService.createNewUser(userDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTOResponse> update(@PathVariable Long id, @RequestBody UserDTORequest userDTORequest){
        UserDTOResponse newUser = userService.updateUser(id, userDTORequest);
        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }

    @PutMapping("/users/{id}/password")
    public ResponseEntity<UserDTOResponse> updatePassWord(@PathVariable Long id, @RequestBody UserDTORequest password){
        UserDTOResponse newPassword = userService.updateUserPassword(id, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(newPassword);
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO user){

        userService.testUserIfExist(user);

        var usernamePassword = new UsernamePasswordAuthenticationToken(user.email(), user.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new UserResponseTokenDTO(token));
    }

//    @PostMapping("/login")
//    public ResponseEntity<Void> login(@RequestBody @Valid UserDTO userDTO) {
//        Authentication authenticationRequest =
//        UsernamePasswordAuthenticationToken.unauthenticated(userDTO.getFirstName(), userDTO.getPassword());
//        Authentication authenticationResponse =
//        this.authenticationManager.authenticate(authenticationRequest);
//        return null;
//    }
//    public ResponseEntity<UserDTO> login(@RequestBody @Valid UserDTO userDTO){
//        userService.showUserWithExist(userDTO);
//        return ResponseEntity.status(HttpStatus.FOUND).build();
//    }
}
