package com.passuol.sp.challenge03.msuser.service;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository repository;

    @Mock
    private UserRepositorySecurity repositorySecurity;

    @Mock
    private UserDTORequestToUserMapper userDTORequestToUserMapper;

    @Mock
    private UserToUserDTOResponseMapper userToUserDTOResponseMapper;

    @Test
    void getUser_ByExistingId_ReturnsUserDTOResponse() {

        User user = new User(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        UserDTOResponse userDTOResponse = new UserDTOResponse("firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com");

        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        when(userToUserDTOResponseMapper.convertUserToUserDTOResponse(user)).thenReturn(userDTOResponse);

        UserDTOResponse sut = userService.getUserById(1L);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(userDTOResponse);

    }

    @Test
    void getUser_ByUnexistingId_ThrowsUserNotFoundException() {

        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99L));

    }

    @Test
    void createUser_WithValidData_ReturnsUserDTOResponse() {

        User user = new User(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        UserDTORequest userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        UserDTOResponse userDTOResponseDB = new UserDTOResponse("firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com");

        when(repository.findByEmail(anyString())).thenReturn(null);
        when(repository.findByCpf(anyString())).thenReturn(null);

        when(userDTORequestToUserMapper.convertInUser(userDTORequest)).thenReturn(user);

        when(repository.save(user)).thenReturn(user);

        when(userToUserDTOResponseMapper.convertUserToUserDTOResponse(user)).thenReturn(userDTOResponseDB);

        UserDTOResponse userDTOResponse = userService.createNewUser(userDTORequest);

        assertThat(userDTOResponse).isEqualTo(userDTOResponseDB);
    }

    @Test
    void createUser_WithEmailExists_ThrowsUserAlreadyEmailExistsException() {

        UserDTORequest userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);

        when(repository.findByEmail(anyString())).thenReturn(new User());

        assertThrows(UserAlreadyEmailExistsException.class, () -> userService.createNewUser(userDTORequest));
    }

    @Test
    void createUser_WithCPFExists_ThrowsUserAlreadyCPFExistsException() {

        UserDTORequest userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);

        when(repository.findByCpf(anyString())).thenReturn(new User());

        assertThrows(UserAlreadyCPFExistsException.class, () -> userService.createNewUser(userDTORequest));

    }

    @Test
    void createUser_WithInvalidData_ThrowsBadRequestException() {

        UserDTORequest userDTORequest = new UserDTORequest(1L, "", "", null,"1990-01-15", "", null, true);

        assertThatThrownBy(() -> userService.createNewUser(userDTORequest)).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void updateUser_WithValidData_ReturnsUserResponseDTO() {

        User userRequest = new User(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        UserDTORequest userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", null, true);
        UserDTOResponse userDTOResponseDB = new UserDTOResponse("firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com");

        when(repository.findById(userRequest.getId())).thenReturn(Optional.of(userRequest));
        when(repository.findByEmail(anyString())).thenReturn(null);
        when(repository.findByCpf(anyString())).thenReturn(null);

        when(repository.save(userRequest)).thenReturn(userRequest);
        when(userToUserDTOResponseMapper.convertUserToUserDTOResponse(userRequest)).thenReturn(userDTOResponseDB);

        UserDTOResponse userDTOResponse = userService.updateUser(1L, userDTORequest);

        assertThat(userDTOResponseDB).isEqualTo(userDTOResponse);

    }

    @Test
    void updateUser_WithNoUserInDBExists_ThrowsUserNotFoundException() {

        UserDTORequest userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", null, true);

        when(repository.findById(99L)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(99L, userDTORequest));

    }

    @Test
    void updateUser_WithEmailExists_ThrowsUserAlreadyEmailExistsException() {
        User user1 = new User(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        User user2 = new User(1L, "firstName", "lastName", "123.456.721-32","1990-01-15", "maria@example.com.br", "password", true);

        when(repository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(repository.findByEmail(anyString())).thenReturn(user2);

        var userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria1@example.com", "password", true);

        assertThrows(UserAlreadyEmailExistsException.class, () -> userService.updateUser(1L, userDTORequest));

    }

    @Test
    void updateUser_WithCPFExists_ThrowsUserAlreadyCPFExistsException() {
        User user1 = new User(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        User user2 = new User(1L, "firstName", "lastName", "123.456.721-32","1990-01-15", "maria@example.com.br", "password", true);

        when(repository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(repository.findByCpf(anyString())).thenReturn(user2);

        var userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria1@example.com", "password", true);

        assertThrows(UserAlreadyCPFExistsException.class, () -> userService.updateUser(1L, userDTORequest));

    }

    @Test
    void updateUser_WithInvalidData_ThrowsIllegalArgumentException() {

        User user1 = new User(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);

        when(repository.findById(anyLong())).thenReturn(Optional.of(user1));

        var userDTORequest = new UserDTORequest(1L, "", "", "","1990-01-15", "maria1example.com", "", true);

        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(1L, userDTORequest));

    }

    @Test
    void updateUserPassword_WithValidData_ReturnsMessage() {

        User userRequest = new User(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        UserDTORequest userDTORequest = new UserDTORequest("1234567");

        when(repository.findById(userRequest.getId())).thenReturn(Optional.of(userRequest));

        when(repository.save(userRequest)).thenReturn(userRequest);

        UserDTOResponse userDTOResponseMessage = userService.updateUserPassword(1L, userDTORequest);

        assertThat(userDTOResponseMessage.getMessage()).isEqualTo("Password updated successfully");

    }

    @Test
    void updateUserPassword_WithBlankOrEmptyPassword_ThrowsUserPasswordInvalid() {

        UserDTORequest userDTORequest = new UserDTORequest(" ");

        assertThrows(UserPasswordInvalid.class, () -> userService.updateUserPassword(1L, userDTORequest));

    }

    @Test
    void updateUserPassword_WithNoUserInDBExists_ThrowsUserNotFoundException() {

        UserDTORequest userDTORequest = new UserDTORequest("1234567");

        when(repository.findById(99L)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userService.updateUserPassword(99L, userDTORequest));

    }

    @Test
    void testUserIfExist_WithUserExists_ReturnVoid() {

        String validEmail = "test@example.com";
        AuthenticationDTO user = new AuthenticationDTO(validEmail, "password");
        User mockUser = new User(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);  // Crie um usuário de exemplo

        when(repository.findByEmail(validEmail)).thenReturn(mockUser);

        assertDoesNotThrow(() -> userService.testUserIfExist(user));

        verify(repository, times(1)).findByEmail(validEmail);

    }
    @Test
    void testUserIfExist_WithUserNotExists_ThrowsUserNotFoundException() {

        String validEmail = "userNotExists@example.com";
        AuthenticationDTO user = new AuthenticationDTO(validEmail, "password");

        when(repository.findByEmail(validEmail)).thenReturn(null);
        assertThrows(UserNotFoundException.class,() -> userService.testUserIfExist(user));

        verify(repository, times(1)).findByEmail(validEmail);

    }
    @Test
    public void loadUserByUsername_WithUserFounded_ReturnsStringUser() {

        String username = "maria@example.com";
        User user = new User(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        when(repositorySecurity.findByEmail(username)).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertNotNull(userDetails);

    }
}
