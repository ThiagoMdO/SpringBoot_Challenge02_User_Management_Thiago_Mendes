package com.passuol.sp.challenge03.msuser.model.dto;

import com.passuol.sp.challenge03.msuser.enuns.UserRole;
import jakarta.validation.constraints.*;
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
public class UserDTORequest {

    private Long id;

    @NotBlank
    @Size(min = 3, message = "The field must to have at least 3 characters")
    private String firstName;

    @NotBlank
    @Size(min = 3, message = "The field must to have at least 3 characters")
    private String lastName;

    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF must be in the format 000.000.000-00")
    @NotBlank
    private String cpf;

    @NotNull
    private LocalDate birthdate;

    @Email(message = "This format is not accept")
    @NotBlank
    private String email;

    @Size(min = 6)
    @NotBlank
    @NotNull
    private String password;

    private Boolean active;

    private UserRole role;

    public UserDTORequest(Long id, String firstName, String lastName, String cpf, String birthDate, String email, String password, Boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.birthdate = LocalDate.parse(birthDate);
        this.email = email;
        this.password = password;
        this.active = active;
    }
    public UserDTORequest(String firstName, String lastName, String cpf, String birthDate, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.birthdate = LocalDate.parse(birthDate);
        this.email = email;
    }

    @Override
    public boolean equals(Object obj){
        return EqualsBuilder.reflectionEquals(obj, this);
    }
}
