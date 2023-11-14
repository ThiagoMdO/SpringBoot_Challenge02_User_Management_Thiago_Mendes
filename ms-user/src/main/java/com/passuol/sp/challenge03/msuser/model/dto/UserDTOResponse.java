package com.passuol.sp.challenge03.msuser.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.passuol.sp.challenge03.msuser.enuns.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
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
    private String message;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private Boolean active;

    @JsonIgnore
    private UserRole role;

    public UserDTOResponse(String firstName, String lastName, String cpf, String birthDate, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.birthdate = LocalDate.parse(birthDate);
        this.email = email;
    }
    public UserDTOResponse(String password){
        this.password = password;
    }

    @Override
    public boolean equals(Object obj){
        return EqualsBuilder.reflectionEquals(obj, this);
    }

}
