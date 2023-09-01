package com.unipi.backend.model;


import javax.persistence.*;

@Entity
@Table(
        name = "user_quiz_answer"
)
public class UserQuizAnswer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private Long userId;
    private Long questionId;
    private Long selectedOption;

    public UserQuizAnswer() {
    }

    public UserQuizAnswer(Long userId, Long questionId, Long selectedOption) {
        this.userId = userId;
        this.questionId = questionId;
        this.selectedOption = selectedOption;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getSelectedOption() {
        return this.selectedOption;
    }

    public void setSelectedOption(Long selectedOption) {
        this.selectedOption = selectedOption;
    }
}
