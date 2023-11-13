package com.passuol.sp.challenge03.msuser.model.entity;

import com.passuol.sp.challenge03.msuser.enuns.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity(name = "userTable")
@Data
public class User implements UserDetails {

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
    @NotBlank
    private String cpf;

    @NotNull
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'12:00:00'Z'")
    private LocalDate birthdate;

    @Email(message = "This format is not accept")
    @NotBlank
    private String email;

    @Size(min = 6)
    @NotBlank
    @NotNull
    private String password;

    private Boolean active;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(){}
    public User(Long id, String firstName, String lastName, String cpf, LocalDate birthdate, String email, String password, Boolean active){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.active = active;
        this.role = UserRole.USER;
    }
    public User(Long id, String firstName, String lastName, String cpf, String birthDate, String email, String password, boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.birthdate = LocalDate.parse(birthDate);
        this.email = email;
        this.password = password;
        this.active = active;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));

    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj){
        return EqualsBuilder.reflectionEquals(obj, this);
    }
}
