package com.quiz.service;

import com.quiz.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class QuestionService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
    private final Map<Integer, Question> questions = new ConcurrentHashMap<>();

    public List<Question> loadQuizzes() {
        logger.debug("Loading all quizzes. Total questions: {}", questions.size());
        return new ArrayList<>(questions.values());
    }

    public void addQuestion(Question question) {
        questions.put(question.getId(), question);
        logger.info("Added new question with ID: {}", question.getId());
    }

    public void editQuestion(Question question) {
        questions.put(question.getId(), question);
        logger.info("Updated question with ID: {}", question.getId());
    }

    public void deleteQuestion(int questionId) {
        if (!questions.containsKey(questionId)) {
            logger.error("Attempted to delete non-existent question with ID: {}", questionId);
            throw new IllegalArgumentException("Question not found");
        }
        questions.remove(questionId);
        logger.info("Deleted question with ID: {}", questionId);
    }


}
