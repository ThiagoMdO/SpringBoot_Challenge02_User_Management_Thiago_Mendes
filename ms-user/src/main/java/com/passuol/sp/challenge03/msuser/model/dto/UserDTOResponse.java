package com.passuol.sp.challenge03.msuser.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.passuol.sp.challenge03.msuser.enuns.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTOResponse {

    @JsonIgnore
    private Long id;

    private String firstName;

    private String lastName;

    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private Boolean active;

    @JsonIgnore
    private UserRole role;

}
