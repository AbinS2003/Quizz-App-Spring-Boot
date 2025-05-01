package com.abin.quizzapp.dto;

public class QuizCreationResponse {
    private Integer quizId;

    public QuizCreationResponse() {}

    public QuizCreationResponse(Integer quizId) {
        this.quizId = quizId;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }
}
