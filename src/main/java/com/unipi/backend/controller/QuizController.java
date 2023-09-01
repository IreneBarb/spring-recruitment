package com.unipi.backend.controller;

import java.util.List;

import com.unipi.backend.model.Question;
import com.unipi.backend.model.Quiz;
import com.unipi.backend.model.UserQuizAnswer;
import com.unipi.backend.service.QuestionsService;
import com.unipi.backend.service.QuizService;
import com.unipi.backend.service.UserQuizAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/quiz"})
public class QuizController {
    @Autowired
    private UserQuizAnswerService userQuizAnswerService;
    @Autowired
    private QuestionsService questionsService;
    @Autowired
    private QuizService quizService;

    public QuizController() {
    }

    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @PostMapping({"/quiz"})
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) throws Exception {
        Quiz savedQuiz = this.quizService.save(quiz);
        return new ResponseEntity(savedQuiz, HttpStatus.CREATED);
    }

    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @PostMapping({"/answer"})
    public ResponseEntity<UserQuizAnswer> saveUserQuizAnswer(@RequestBody UserQuizAnswer answer) throws Exception {
        UserQuizAnswer savedAnswer = this.userQuizAnswerService.saveUserQuizAnswer(answer);
        return new ResponseEntity(savedAnswer, HttpStatus.CREATED);
    }

    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @GetMapping({"/answers/{userId}"})
    public ResponseEntity<List<UserQuizAnswer>> getUserQuizAnswers(@PathVariable Long userId) {
        List<UserQuizAnswer> answers = this.userQuizAnswerService.getUserQuizAnswers(userId);
        return new ResponseEntity(answers, HttpStatus.OK);
    }

    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @PostMapping({"/questions"})
    public ResponseEntity<Question> saveQuizQuestion(@RequestBody Question question) throws Exception {
        Question savedQuestion = this.questionsService.save(question);
        return new ResponseEntity(savedQuestion, HttpStatus.CREATED);
    }

    @CrossOrigin(
            origins = {"http://localhost:4200"}
    )
    @GetMapping({"/questions/{quizId}"})
    public ResponseEntity<List<Question>> getQuizQuestions(@PathVariable Integer quizId) {
        List<Question> questions = this.questionsService.getQuizQuestions(quizId);
        return new ResponseEntity(questions, HttpStatus.OK);
    }
}