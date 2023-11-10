package com.passuol.sp.challenge03.msuser.model.dto;

import com.passuol.sp.challenge03.msuser.enuns.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTORequest {

    private Long id;

    private String firstName;

    private String lastName;

    private String cpf;

//    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

    private String email;

    private String password;

    private Boolean active;

    private UserRole role;

}
