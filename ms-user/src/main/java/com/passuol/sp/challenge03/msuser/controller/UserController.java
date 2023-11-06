package com.passuol.sp.challenge03.msuser.controller;

import com.passuol.sp.challenge03.msuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1")
@RestController
public class UserController {

    private final UserService userService;


}

