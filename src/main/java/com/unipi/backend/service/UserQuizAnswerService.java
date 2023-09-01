package com.unipi.backend.service;

import java.util.Iterator;
import java.util.List;

import com.unipi.backend.model.UserQuizAnswer;
import com.unipi.backend.repository.UserQuizAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQuizAnswerService {
    @Autowired
    private UserQuizAnswerRepository userQuizAnswerRepository;

    public UserQuizAnswerService() {
    }

    public UserQuizAnswer saveUserQuizAnswer(UserQuizAnswer answer) throws Exception {
        List<UserQuizAnswer> userAnswers = this.getUserQuizAnswers(answer.getUserId());
        Iterator var3 = userAnswers.iterator();

        UserQuizAnswer existingAnswer;
        do {
            if (!var3.hasNext()) {
                return (UserQuizAnswer)this.userQuizAnswerRepository.save(answer);
            }

            existingAnswer = (UserQuizAnswer)var3.next();
        } while(existingAnswer.getQuestionId() != answer.getQuestionId());

        throw new Exception();
    }

    public List<UserQuizAnswer> getUserQuizAnswers(Long userId) {
        return this.userQuizAnswerRepository.findByUserId(userId);
    }
}
