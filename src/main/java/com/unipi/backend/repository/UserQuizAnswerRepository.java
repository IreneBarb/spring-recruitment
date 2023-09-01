package com.unipi.backend.repository;

import java.util.List;

import com.unipi.backend.model.UserQuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuizAnswerRepository extends JpaRepository<UserQuizAnswer, Long> {
    List<UserQuizAnswer> findByUserId(Long userId);
}