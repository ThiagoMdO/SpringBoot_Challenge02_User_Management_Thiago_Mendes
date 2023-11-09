package com.passuol.sp.challenge03.msuser.controller;

import com.passuol.sp.challenge03.msuser.model.dto.AuthenticationDTO;
import com.passuol.sp.challenge03.msuser.model.dto.UserDTO;
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

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> find(@PathVariable("id") Long id){
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO){
        UserDTO newUser = userService.createNewUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO){
        userService.updateUser(id, userDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/users/{id}/password")
    public ResponseEntity<Void> updatePassWord(@PathVariable Long id, @RequestBody UserDTO password){
        userService.updateUserPassword(id, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO user){

        var usernamePassword = new UsernamePasswordAuthenticationToken(user.email(), user.password());

        var auth = authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
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
