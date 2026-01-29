package com.quiz.service;

import com.quiz.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceTest {

    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionService();
    }

    @Test
    void testLoadQuizzes_InitiallyEmpty() {
        List<Question> questions = questionService.loadQuizzes();
        assertNotNull(questions);
        assertTrue(questions.isEmpty());
    }

    @Test
    void testAddQuestion() {
        Question question = new Question(1, "What is Java?", "A programming language",
                new ArrayList<>(List.of("A programming language", "A coffee", "An island", "A city")));

        questionService.addQuestion(question);

        List<Question> questions = questionService.loadQuizzes();
        assertEquals(1, questions.size());
        assertEquals("What is Java?", questions.get(0).getQuestionText());
    }

    @Test
    void testAddMultipleQuestions() {
        Question question1 = new Question(1, "Question 1", "Answer 1",
                new ArrayList<>(List.of("Answer 1", "Answer 2", "Answer 3", "Answer 4")));
        Question question2 = new Question(2, "Question 2", "Answer 2",
                new ArrayList<>(List.of("Answer 1", "Answer 2", "Answer 3", "Answer 4")));

        questionService.addQuestion(question1);
        questionService.addQuestion(question2);

        List<Question> questions = questionService.loadQuizzes();
        assertEquals(2, questions.size());
    }

    @Test
    void testEditQuestion() {
        Question original = new Question(1, "Original Question", "Original Answer",
                new ArrayList<>(List.of("Option 1", "Option 2", "Option 3", "Option 4")));
        questionService.addQuestion(original);

        Question updated = new Question(1, "Updated Question", "Updated Answer",
                new ArrayList<>(List.of("New 1", "New 2", "New 3", "New 4")));
        questionService.editQuestion(updated);

        List<Question> questions = questionService.loadQuizzes();
        assertEquals(1, questions.size());
        assertEquals("Updated Question", questions.get(0).getQuestionText());
        assertEquals("Updated Answer", questions.get(0).getCorrectAnswer());
    }

    @Test
    void testDeleteQuestion() {
        Question question = new Question(1, "Test Question", "Test Answer",
                new ArrayList<>(List.of("Answer 1", "Answer 2", "Answer 3", "Answer 4")));
        questionService.addQuestion(question);

        questionService.deleteQuestion(1);

        List<Question> questions = questionService.loadQuizzes();
        assertTrue(questions.isEmpty());
    }

    @Test
    void testDeleteQuestion_ThrowsExceptionWhenNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            questionService.deleteQuestion(999);
        });
    }

    @Test
    void testEditQuestion_ReplacesExistingQuestion() {
        Question question1 = new Question(1, "Question 1", "Answer 1",
                new ArrayList<>(List.of("A", "B", "C", "D")));
        Question question2 = new Question(2, "Question 2", "Answer 2",
                new ArrayList<>(List.of("A", "B", "C", "D")));
        questionService.addQuestion(question1);
        questionService.addQuestion(question2);

        Question updated = new Question(1, "Updated Question 1", "New Answer",
                new ArrayList<>(List.of("W", "X", "Y", "Z")));
        questionService.editQuestion(updated);

        List<Question> questions = questionService.loadQuizzes();
        assertEquals(2, questions.size());
        
        Question updatedQuestion = questions.stream()
                .filter(q -> q.getId() == 1)
                .findFirst()
                .orElse(null);
        
        assertNotNull(updatedQuestion);
        assertEquals("Updated Question 1", updatedQuestion.getQuestionText());
        assertEquals("New Answer", updatedQuestion.getCorrectAnswer());
    }
}
