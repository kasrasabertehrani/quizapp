# Quiz App

![Java](https://img.shields.io/badge/Java-17-blue) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.1-brightgreen?logo=spring-boot) ![Thymeleaf](https://img.shields.io/badge/Views-Thymeleaf-005F0F?logo=thymeleaf) ![Docker](https://img.shields.io/badge/Docker-Multi--Stage-2CA5E0?logo=docker&logoColor=white)

A browser-based multiple-choice quiz application. Users can register, sign in, answer questions, and view their score; administrators can add, edit, and delete questions.

This project demonstrates:

- Spring MVC with server-rendered Thymeleaf views
- Form-based authentication and role-based access rules
- Password hashing with BCrypt
- Question management and quiz scoring
- Unit testing, CI workflows, and Docker packaging

## Demo

### Taking a Quiz

Select answers, submit the quiz, and view the score.
<img width="2340" height="1312" alt="questionsGif (1)" src="https://github.com/user-attachments/assets/b86cf56a-f3ae-4c78-8335-ededdb27a76d" />

### Managing Questions

Add a question, choose the correct answer, and edit it from the admin interface.

<img width="1124" height="614" alt="manageQuestionGIF-compressed" src="https://github.com/user-attachments/assets/c5215c79-1f01-48cb-90ba-68d9a412b99a" />



## Tech Stack

| Area | Technologies |
| --- | --- |
| Application | Java 17, Spring Boot 4.0.1, Maven Wrapper |
| Web interface | Thymeleaf, HTML, CSS |
| Authentication & authorization | Spring Security, BCrypt |
| Architecture | Spring MVC with controller, service, and model classes |
| Storage | In-memory `ConcurrentHashMap` collections |
| Testing | JUnit 5, Spring Boot Test |
| Containerization | Multi-stage Docker build, Docker Compose |
| CI | GitHub Actions, Maven test reports, OWASP Dependency-Check |

## Where to Run

### Docker

With Git installed and Docker running with Compose support:

1. Clone the repository and enter its root directory:

   ```bash
   git clone https://github.com/kasrasabertehrani/quizapp.git
   cd quizapp
   ```

2. Build and start the application:

   ```bash
   docker compose up --build
   ```

3. Open [http://localhost:8080](http://localhost:8080) in your browser. Port `8080` must be available.

### Maven

Alternatively, with JDK 17 installed, run from the repository root:

```bash
# Linux / macOS
chmod +x mvnw
./mvnw spring-boot:run
```

On Windows PowerShell, use `.\mvnw.cmd spring-boot:run`.

## How to Use

1. Register an **ADMIN** account in the local demo, then sign in. Opening the question-management page adds two sample questions if the question bank is empty.
2. Use the admin interface to add, edit, or delete questions.
3. Sign out and register a **USER** account to try the quiz-taking flow.
4. Sign in, select an answer for each question, and submit to view the score.

The current registration form lets visitors choose either role. This is suitable for exploring the local demo; public registration should assign `USER` on the server and restrict how administrator accounts are created.

## Architecture & Design

The application uses **Spring MVC** with separate controller, service, model, and view components.

- **Controller & Views:** `QuizController` handles registration, question-management requests, and quiz scoring. Thymeleaf templates render the login, registration, admin, quiz, and results pages.
- **Services & Models:** `QuestionService` manages questions, while `QuizUserDetailsService` registers users and supplies user details to Spring Security. `Question` and `User` represent the application data.
- **Security:** `WebSecurityConfig` defines form login, logout, and route-level role checks. `PasswordEncoderConfig` supplies BCrypt password hashing. CSRF protection remains enabled.

Users and questions are stored in memory, so registered accounts and question changes are lost when the application restarts.

## Testing & CI

Run the existing service tests and application-context test with `./mvnw test` (`.\mvnw.cmd test` on Windows).

The [GitHub Actions workflow](.github/workflows/ci-cd.yml) is configured for pushes to `main` and `develop`, and pull requests targeting `main`. It builds the application, runs tests, uploads the JAR, and runs a non-blocking OWASP dependency scan. Pushes to `main` also build a Docker image; the workflow does not publish the image or deploy the application.


## Author

[Kasra Sabertehrani](https://github.com/kasrasabertehrani)
