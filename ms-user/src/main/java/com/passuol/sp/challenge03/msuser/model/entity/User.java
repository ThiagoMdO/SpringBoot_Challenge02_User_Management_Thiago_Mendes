package com.passuol.sp.challenge03.msuser.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.passuol.sp.challenge03.msuser.config.LocalDateTimeSerializer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "userTable")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, message = "The field must to have at least 3 characters")
    private String firstName;

    @NotBlank
    @Size(min = 3, message = "The field must to have at least 3 characters")
    private String lastName;

    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF must be in the format 000.000.000-00")
    private String cpf;

    @NotNull
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime birthdate;

    @Email(message = "This format is not accept")
    private String email;

    @Min(6)
    private String password;

    private Boolean active;
}
