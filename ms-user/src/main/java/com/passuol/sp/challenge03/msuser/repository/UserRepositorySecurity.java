package com.passuol.sp.challenge03.msuser.repository;

import com.passuol.sp.challenge03.msuser.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepositorySecurity extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);
}
