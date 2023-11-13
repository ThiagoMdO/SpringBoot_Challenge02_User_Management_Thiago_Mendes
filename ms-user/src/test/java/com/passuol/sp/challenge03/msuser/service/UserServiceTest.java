package com.passuol.sp.challenge03.msuser.service;

import com.passuol.sp.challenge03.msuser.exception.UserAlreadyCPFExistsException;
import com.passuol.sp.challenge03.msuser.exception.UserAlreadyEmailExistsException;
import com.passuol.sp.challenge03.msuser.exception.UserNotFoundException;
import com.passuol.sp.challenge03.msuser.model.dto.UserDTORequest;
import com.passuol.sp.challenge03.msuser.model.dto.UserDTOResponse;
import com.passuol.sp.challenge03.msuser.model.entity.User;
import com.passuol.sp.challenge03.msuser.repository.UserRepository;
import com.passuol.sp.challenge03.msuser.service.mapper.UserDTORequestToUserMapper;
import com.passuol.sp.challenge03.msuser.service.mapper.UserToUserDTOResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@RequiredArgsConstructor
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository repository;

    @Mock
    private UserDTORequestToUserMapper userDTORequestToUserMapper;
    @Mock
    private UserToUserDTOResponseMapper userToUserDTOResponseMapper;

    @Test
    void getUser_ByExistingId_ReturnsUserDTOResponse() {

        User user = new User(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        UserDTOResponse userDTOResponse = new UserDTOResponse("firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com");
        //UserDTORequest userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "cpf","1990-01-15", "maria@example.com", "password", true);
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        when(userToUserDTOResponseMapper.convertUserToUserDTOResponse(user)).thenReturn(userDTOResponse);
        //when(userToUserDTOResponseMapper.convertUserToUserDTOResponse(USER)).thenReturn(USER_DTO_RESPONSE);

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
        // Arrange
        User user = new User(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        UserDTORequest userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        UserDTOResponse userDTOResponseDB = new UserDTOResponse("firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com");

        when(repository.findByEmail(anyString())).thenReturn(null);
        when(repository.findByCpf(anyString())).thenReturn(null);

        when(userDTORequestToUserMapper.convertInUser(userDTORequest)).thenReturn(user);

        when(repository.save(user)).thenReturn(user);

        when(userToUserDTOResponseMapper.convertUserToUserDTOResponse(user)).thenReturn(userDTOResponseDB);

        // Act
        UserDTOResponse userDTOResponse = userService.createNewUser(userDTORequest);

        // Assert
        //assertThat(userDTORequest.toString().isNotBlank().isNotNull();
        assertThat(userDTOResponse).isEqualTo(userDTOResponseDB);
    }
    @Test
    void createUser_WithEmailExists_ThrowsUserAlreadyEmailExistsException() {
        // Arrange
        UserDTORequest userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);

        // Configurar comportamento do mock para findByCpf retornar um usuário existente
        when(repository.findByEmail(anyString())).thenReturn(new User());

        // Act e Assert
        assertThrows(UserAlreadyEmailExistsException.class, () -> userService.createNewUser(userDTORequest));
        // Adicione mais asserções conforme necessário
    }
    @Test
    void createUser_WithCPFExists_ThrowsUserAlreadyCPFExistsException() {
        // Arrange
        UserDTORequest userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);

        // Configurar comportamento do mock para findByCpf retornar um usuário existente
        when(repository.findByCpf(anyString())).thenReturn(new User());

        // Act e Assert
        assertThrows(UserAlreadyCPFExistsException.class, () -> userService.createNewUser(userDTORequest));
        // Adicione mais asserções conforme necessário
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

//    @Test
//    void updateUser_WithExistingEmail_ThrowsUserAlreadyEmailExistsException() {
//        // Arrange
//        User userRequest = new User(1L, "firstName", "lastName", "123.456.721-31", "1990-01-15", "maria@example.com", "password", true);
//        UserDTORequest userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31", "1990-01-15", "maria@example.com", "password", true);
//
//        when(repository.findById(userRequest.getId())).thenReturn(Optional.of(userRequest));
//        when(repository.findByEmail(userDTORequest.getEmail())).thenReturn(userRequest);
//
//        // Act & Assert
//        assertThrows(UserAlreadyEmailExistsException.class, () -> userService.updateUser(1L, userDTORequest));
//    }
    @Test
    void updateUser_WithEmailExists_ThrowsUserAlreadyEmailExistsException() {
        User userRequest = new User(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        User user2Response = new User(2L, "firstName", "lastName", "123.456.721-34","1990-01-15", "maria@example.com", "password", true);

        // Arrange
        UserDTORequest userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);
        UserDTORequest userDTO2Request = new UserDTORequest(2L, "firstName", "lastName", "123.456.721-34","1990-01-15", "maria@example.com", "password", true);

//        UserDTORequest user2DTORequest = new UserDTORequest("firstName", "lastName", "123.456.721-34","1990-01-15", "joao@example.com");

        when(repository.findById(userRequest.getId())).thenReturn(Optional.of(userRequest));
        when(repository.findByEmail(anyString())).thenReturn(user2Response);
//        when(repository.findByCpf(user2Response.getCpf())).thenReturn(userRequest);

        //UserDTOResponse userDTOResponse = userService.updateUser(userRequest.getId(), userDTORequest);


//        assertThat(user2Response.getEmail()).isNotNull();
//        assertThat(user2Response.getEmail()).isEqualTo(userRequest.getEmail());

        assertThrows(UserAlreadyEmailExistsException.class, () ->  userService.updateUser(1L, userDTORequest));
//        assertThrows(UserAlreadyEmailExistsException.class, () -> userService.updateUser(userRequest.getId(), userDTO2Request)/userResponseEmail != null && !Objects.equals(userRequest.getEmail(), userResponseEmail.getEmail()));
//        assertThatThrownBy(() -> userService.updateUser(userRequest.getId(), userDTORequest)).isInstanceOf(UserAlreadyEmailExistsException.class);
        // Configurar comportamento do mock para findByCpf retornar um usuário existente
        //when(repository.findByEmail(user2Response.getEmail())).thenThrow(UserAlreadyEmailExistsException.class);
        //assertThat(userDTOResponse.getEmail()).isEqualTo(user2Response.getEmail());
        // Act e Assert
        // Adicione mais asserções conforme necessário
    }

    @Test
    void updateUser_WithCPFExists_ThrowsUserAlreadyCPFExistsException() {
        // Arrange
        UserDTORequest userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "123.456.721-31","1990-01-15", "maria@example.com", "password", true);

        // Configurar comportamento do mock para findByCpf retornar um usuário existente
        when(repository.findByCpf(anyString())).thenReturn(new User());

        // Act e Assert
        assertThrows(UserAlreadyCPFExistsException.class, () -> userService.updateUser(1L, userDTORequest));
        // Adicione mais asserções conforme necessário
    }
    @Test
    void updateUser_WithInvalidData_ThrowsBadRequest() {

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





//    @Test
//    public void createUser_WithValidData_ReturnsUserDTOResponse(){
//        //AAA
//        //Arrange
//        User user = new User(1L, "firstName", "lastName", "cpf","1990-01-15", "maria@example.com", "password", true);
//        UserDTORequest userDTORequest = new UserDTORequest(1L, "firstName", "lastName", "cpf","1990-01-15", "maria@example.com", "password", true);
//        UserDTOResponse userDTOResponse = new UserDTOResponse("firstName", "lastName", "cpf","1990-01-15", "maria@example.com");
//
//
////        when(repository.save(user)).thenReturn(user);
////        when(userService.createNewUser(userDTORequest)).thenReturn(userDTOResponse);
//        //system under test (sut) - convenção
//        //Act - action
//        UserDTOResponse sut = userService.createNewUser(userDTORequest);
//
//        //Assert - aferir
//        assertThat(sut).isEqualTo(userDTOResponse);
//    }
//    @Test
//    public void createUser_WithValidData_ReturnsUserDTOResponse(){
//
//        when(repository.findByEmail("joao@test.com")).thenReturn(null);
//        when(repository.findByCpf("448.555.777.111")).thenReturn(null);
//
//        User userResponseEmail = repository.findByEmail("joao@test.com");
//        User userResponseCpf = repository.findByCpf("448.555.777.111");
//
//        assertThat(userResponseEmail).isNull();
//        assertThat(userResponseCpf).isNull();
//
//        when(userDTORequestToUserMapper.convertInUser(USER_DTO_REQUEST)).thenReturn(USER);
//
//        User userResponse = USER;
//
//        String encryptedPassword = new BCryptPasswordEncoder().encode(USER_DTO_REQUEST.getPassword());
//        USER.setPassword(encryptedPassword);
//
//        USER.setRole(userRole.USER);
//
//        when(repository.save(USER)).thenReturn(USER);
//
//        when(userToUserDTOResponseMapper.convertUserToUserDTOResponse(USER)).thenReturn(USER_DTO_RESPONSE);
//
//
//
//    }

//    @Test
//    public void createUser_WithValidData_ReturnsUserDTOResponse12() {
//        // Criando um objeto de solicitação simulado
//        UserDTORequest userDTORequest = USER_DTO_REQUEST;
//
//        // Configurando o comportamento dos mocks
//        when(repository.findByEmail(userDTORequest.getEmail())).thenReturn(null);
//        when(repository.findByCpf(userDTORequest.getCpf())).thenReturn(null);
//
//        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTORequest.getPassword());
//        User userToSave = USER;
//        userToSave.setPassword(encryptedPassword);
//        userToSave.setRole(userRole.USER);
//
//        when(userDTORequestToUserMapper.convertInUser(userDTORequest)).thenReturn(userToSave);
//        when(repository.save(userToSave)).thenReturn(userToSave);
//        when(userToUserDTOResponseMapper.convertUserToUserDTOResponse(userToSave)).thenReturn(USER_DTO_RESPONSE);
//
//        // Chamando o método sob teste
//        UserDTOResponse response = userService.createNewUser(userDTORequest);
//
//        // Verificando se o resultado é o esperado
//        assertThat(response).isNotNull();
//        // Adicione mais verificações conforme necessário
//
//        // Verificando se os métodos dos mocks foram chamados corretamente
//        // Note que você pode precisar ajustar conforme necessário com base na sua lógica
//        // when(repository.findByEmail(userDTORequest.getEmail())).thenReturn(null);
//        // when(repository.findByCpf(userDTORequest.getCpf())).thenReturn(null);
//        // when(userDTORequestToUserMapper.convertInUser(userDTORequest)).thenReturn(userToSave);
//        // when(repository.save(userToSave)).thenReturn(userToSave);
//        // when(userToUserDTOResponseMapper.convertUserToUserDTOResponse(userToSave)).thenReturn(/* preencher campos necessários */);
//    }
}

