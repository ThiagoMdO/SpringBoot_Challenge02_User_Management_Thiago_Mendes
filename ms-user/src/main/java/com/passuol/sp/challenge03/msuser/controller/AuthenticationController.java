package com.passuol.sp.challenge03.msuser.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v11")
public class AuthenticationController {

//    private final AuthenticationManager authenticationManager;
//
//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO user){
//
//        var usernamePassword = new UsernamePasswordAuthenticationToken(user.email(), user.password());
//
//        var auth = authenticationManager.authenticate(usernamePassword);
//
//        return ResponseEntity.ok().build();
//    }
}
