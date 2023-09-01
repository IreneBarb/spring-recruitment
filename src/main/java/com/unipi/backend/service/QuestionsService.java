package com.unipi.backend.service;

import java.util.List;

import com.unipi.backend.model.Question;
import com.unipi.backend.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionsService {
    @Autowired
    private QuestionsRepository questionsRepository;

    public QuestionsService() {
    }

    public List<Question> getQuizQuestions(Integer quizId) {
        return this.questionsRepository.findByQuizId(quizId);
    }

    public Question save(Question question) {
        return this.questionsRepository.save(question);
    }
}
