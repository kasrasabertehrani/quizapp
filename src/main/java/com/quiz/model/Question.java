package com.quiz.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;

public class Question {
    private int id;

    @NotBlank(message = "Question text is required")
    @Size(min = 10, max = 500, message = "Question text must be between 10 and 500 characters")
    private String questionText;

    @NotBlank(message = "Correct answer is required")
    private String correctAnswer;

    @NotEmpty(message = "Options are required")
    @Size(min = 2, max = 10, message = "Must have between 2 and 10 options")
    private ArrayList<String> options;

    public Question(int id, String questionText, String correctAnswer, ArrayList<String> options) {
        this.id = id;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getQuestionText() {
        return questionText;
    }
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    public ArrayList<String> getOptions() {
        return options;
    }
    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
    public String toString() {
        return "Question{id=" + id + ", questionText='" + questionText + "', correctAnswer='" + correctAnswer + "', options=" + options + "}";
    }
}
