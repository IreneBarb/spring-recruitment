package com.unipi.backend.service;

import com.unipi.backend.model.Question;
import com.unipi.backend.model.Quiz;
import com.unipi.backend.repository.QuestionsRepository;
import com.unipi.backend.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    public QuizService() {
    }

    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }
}
