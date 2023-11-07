package com.passuol.sp.challenge03.msuser.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String cpf;

    //@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime birthdate;

    private String email;

    private String password;

    private Boolean active;
}
