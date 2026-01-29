package com.quiz.service;

import com.quiz.model.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {
    private final Map<Integer, Question> questions = new HashMap<>();

    public List<Question> loadQuizzes() {
        return new ArrayList<>(questions.values());
    }

    public void addQuestion(Question question) {
        questions.put(question.getId(), question);
    }

    public void editQuestion(Question question) {

        questions.put(question.getId(), question);
    }

    public void deleteQuestion(int questionId) {
        if (!questions.containsKey(questionId)) {
            throw new IllegalArgumentException("Question not found");
        }
        questions.remove(questionId);
    }


}
