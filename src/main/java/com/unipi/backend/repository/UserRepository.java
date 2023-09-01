package com.unipi.backend.repository;

import com.unipi.backend.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
