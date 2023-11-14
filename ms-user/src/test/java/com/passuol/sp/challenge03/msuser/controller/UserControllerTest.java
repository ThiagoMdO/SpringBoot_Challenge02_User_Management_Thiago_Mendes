package com.passuol.sp.challenge03.msuser.controller;

import com.passuol.sp.challenge03.msuser.model.dto.UserDTORequest;
import com.passuol.sp.challenge03.msuser.model.dto.UserDTOResponse;
import com.passuol.sp.challenge03.msuser.repository.UserRepository;
import com.passuol.sp.challenge03.msuser.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserController userController;

    @Test
    void find_ByIdFound_Returns200UserDTOResponse(){

        Long userId = 1L;
        UserDTOResponse userDTOResponse = new UserDTOResponse("firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com");
        when(userService.getUserById(userId)).thenReturn(userDTOResponse);

        ResponseEntity<UserDTOResponse> response = userController.find(userId);

        assertEquals(ResponseEntity.ok().body(userDTOResponse), response);

        verify(userService, times(1)).getUserById(userId);
    }
    @Test
    void create_ByIdFound_Returns201UserDTOResponse(){

        UserDTORequest userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        UserDTOResponse createdUserDTO = new UserDTOResponse("firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com");

        when(userService.createNewUser(userDTORequest)).thenReturn(createdUserDTO);

        ResponseEntity<UserDTOResponse> response = userController.create(userDTORequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdUserDTO, response.getBody());
        verify(userService, times(1)).createNewUser(userDTORequest);
    }
    @Test
    void update_WithValidData_Returns200UserDTOResponse(){

        UserDTORequest existingUserDTO = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        UserDTOResponse createdUserDTO = new UserDTOResponse("firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com");

        UserDTORequest createdUserDTO2 = new UserDTORequest("firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com");
        UserDTOResponse existingUserDTO2 = new UserDTOResponse("firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com");

        when(userService.updateUser(1L, createdUserDTO2)).thenReturn(existingUserDTO2);
        ResponseEntity<UserDTOResponse> response = userController.update(existingUserDTO.getId(), createdUserDTO2);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(createdUserDTO, response.getBody());
    }

    @Test
    void updatePassword_WithValidData_Returns204(){

        UserDTORequest existingUserDTO = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        UserDTORequest newPassword = new UserDTORequest("1245667");
        UserDTOResponse newPassword2 = new UserDTOResponse("1245667");

        when(userService.updateUserPassword(1L, newPassword)).thenReturn(newPassword2);

        ResponseEntity<UserDTOResponse> response = userController.updatePassWord(existingUserDTO.getId(), newPassword);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        assertNotEquals(existingUserDTO.getPassword(), response.getBody().getPassword());
    }

}
