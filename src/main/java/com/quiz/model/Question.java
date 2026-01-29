package com.quiz.model;

import java.util.ArrayList;

public class Question {
    private int id;
    private String questionText;
    private String correctAnswer;
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
