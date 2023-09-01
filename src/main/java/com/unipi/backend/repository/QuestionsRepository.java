package com.unipi.backend.repository;

import java.util.List;

import com.unipi.backend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Integer> {
    List<Question> findByQuizId(Integer quizId);
}