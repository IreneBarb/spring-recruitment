package com.unipi.backend.model;


import javax.persistence.*;

@Entity
@Table(
        name = "question"
)
public class Question {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    @Column
    private int correctOption;
    private int quizId;

    public Question() {
    }

    public Question(String questionText, String option1, String option2, String option3, String option4, int correctOption, int quizId) {
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctOption = correctOption;
        this.quizId = quizId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getCorrectOption() {
        return this.correctOption;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }

    public String getOption1() {
        return this.option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return this.option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return this.option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return this.option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getQuizId() {
        return this.quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }
}
