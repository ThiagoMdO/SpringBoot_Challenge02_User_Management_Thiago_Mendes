package com.passuol.sp.challenge03.msuser.service;

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


}
