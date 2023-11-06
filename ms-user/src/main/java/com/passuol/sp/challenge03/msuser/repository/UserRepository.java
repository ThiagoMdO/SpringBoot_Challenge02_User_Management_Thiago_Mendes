package com.passuol.sp.challenge03.msuser.repository;

import com.passuol.sp.challenge03.msuser.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
