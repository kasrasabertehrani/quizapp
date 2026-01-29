package com.quiz.controller;

import com.quiz.model.Question;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.quiz.service.QuestionService;

@Controller
public class QuizController {

    private final AuthenticationManager authenticationManager;
    private final com.quiz.service.QuizUserDetailsService quizUserDetailsService;
    private final QuestionService questionService;

    public QuizController(AuthenticationManager authenticationManager, com.quiz.service.QuizUserDetailsService quizUserDetailsService, QuestionService questionService) {
        this.authenticationManager = authenticationManager;
        this.quizUserDetailsService = quizUserDetailsService;
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }


    @GetMapping("/home")
    public String greet(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if user is authenticated
        if (authentication == null || !authentication.isAuthenticated() ||
                Objects.equals(authentication.getPrincipal(), "anonymousUser")) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        System.out.println("Username from context " + username);

        // Check user role and redirect accordingly
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).filter(Objects::nonNull)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (isAdmin) {
            return "redirect:/quizlist";
        } else {
            return "redirect:/quiz";
        }
    }
    @GetMapping("/login")
    public String login() {
        return "login"; // Returns the login.html template
    }
    @GetMapping("/register")
    public String register() {
        return "register"; // Returns the register.html template
    }
    // POST endpoint to handle user registration
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username, // Username from the form
            @RequestParam String password, // Password from the form
            @RequestParam String email, // Email from the form
            @RequestParam String role
    ) {
        // Register the user by storing their details in the HashMap
        try {
            quizUserDetailsService.registerUser(username, password, role, email);
        } catch (Exception userExistsAlready) {
            // Redirect to the /register endpoint
            return "redirect:/register?error";
        }
        // Authenticate the user programmatically
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Redirect to the /login endpoint with success message
        return "redirect:/login?success";
    }
    @GetMapping("/quizlist")
    public String quizList(Model model) {
        List<Question> questionList = questionService.loadQuizzes();

        // Only add sample questions if the list is empty
        if (questionList.isEmpty()) {
            Question question1 = new Question(1, "What is the capital of France?", "Paris",
                    new ArrayList<>(List.of("Paris", "London", "Berlin", "Madrid")));
            Question question2 = new Question(2, "What is 2 + 2?", "4",
                    new ArrayList<>(List.of("3", "4", "5", "6")));
            questionService.addQuestion(question1);
            questionService.addQuestion(question2);
            questionList = questionService.loadQuizzes();
        }

        model.addAttribute("questions", questionList);
        return "quizlist";
    }
    @GetMapping("/editquiz/{id}")
    public String editQuiz(@PathVariable int id, Model model) {
        // Access the questions HashMap directly through the service
        List<Question> allQuestions = questionService.loadQuizzes();
        Question question = allQuestions.stream()
                .filter(q -> q.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        model.addAttribute("question", question);
        return "editquiz";
    }
    @PutMapping("/editquiz")
    public String updateQuestion(@ModelAttribute Question question) {
        questionService.editQuestion(question);
        return "redirect:/quizlist";
    }

    @DeleteMapping("/question/delete/{id}")
    public String DeleteQuiz(@PathVariable int id) {
        questionService.deleteQuestion(id);
        return "redirect:/quizlist";
    }

    @GetMapping("/addquiz")
    public String AddQuizPage() {
        return "addquiz";
    }

    @PostMapping("/addquiz")
    public String addQuiz(
            @RequestParam("questionText") String questionText,
            @RequestParam("options[0]") String option1,
            @RequestParam("options[1]") String option2,
            @RequestParam("options[2]") String option3,
            @RequestParam("options[3]") String option4,
            @RequestParam("correctAnswer") String correctAnswer) {

        // Create options list
        ArrayList<String> options = new ArrayList<>();
        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);

        // Generate unique ID
        int newId = questionService.loadQuizzes().stream()
                .mapToInt(Question::getId)
                .max()
                .orElse(0) + 1;

        // Create question using parameterized constructor
        Question questionAdd = new Question(newId, questionText, correctAnswer, options);

        questionService.addQuestion(questionAdd);
        return "redirect:/quizlist";
    }


    @GetMapping("/quiz")
    public String quiz(Model model) {
        List<Question> questions = questionService.loadQuizzes();
        model.addAttribute("questions", questions);
        return "quiz";
    }

    @PostMapping("/quiz")
    public String submitQuiz(@RequestParam Map<String, String> answers, Model model) {
        List<Question> questions = questionService.loadQuizzes();
        int correctCount = 0;

        for (Question question : questions) {
            String userAnswer = answers.get("answer_" + question.getId());
            boolean isCorrect = question.getCorrectAnswer().equals(userAnswer);

            if (isCorrect) {
                correctCount++;
            }
        }

        // Add results to model
        model.addAttribute("score", correctCount);

        return "result";
    }



}
