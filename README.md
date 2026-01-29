# Quiz Application

A secure web-based quiz application built with Spring Boot 4.0.1 and Spring Security 6, demonstrating role-based access control, authentication, and modern web development practices.

## 🚀 Features

- **Authentication & Authorization**: Secure login/registration system using Spring Security
- **Role-Based Access Control**: 
  - **Admin Role**: Can create, edit, and delete quiz questions
  - **User Role**: Can take quizzes and view results
- **Quiz Management**: 
  - Create multiple-choice questions
  - Edit existing questions
  - Delete questions
  - View all questions (Admin only)
- **User Experience**:
  - Clean and responsive UI with Thymeleaf templates
  - Modern gradient design
  - Instant quiz results
  - CSRF protection

## 🛠️ Technologies Used

- **Backend Framework**: Spring Boot 4.0.1
- **Security**: Spring Security 6
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven
- **Java Version**: 17
- **Password Encryption**: BCrypt

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+

## 🔧 Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/kasrasabertehrani/quizapp.git
   cd quizapp
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the application**
   
   Open your browser and navigate to: `http://localhost:8080`

## 📖 Usage

### User Registration

1. Navigate to the registration page
2. Enter username, email, password
3. Select role (USER or ADMIN)
4. Click "Register"

### Taking a Quiz (User Role)

1. Log in with USER credentials
2. View available questions
3. Select answers for each question
4. Submit the quiz
5. View your score

### Managing Quizzes (Admin Role)

1. Log in with ADMIN credentials
2. View all quiz questions
3. Add new questions with multiple-choice options
4. Edit existing questions
5. Delete questions

## 🔐 Security Features

- **Password Encryption**: All passwords are encrypted using BCrypt
- **CSRF Protection**: Built-in CSRF token validation
- **Session Management**: Secure session handling with Spring Security
- **Role-Based Authorization**: Different access levels for admins and users
- **Form-Based Authentication**: Custom login page with security filters

## 📁 Project Structure

```
quizapp/
├── src/
│   ├── main/
│   │   ├── java/com/quiz/
│   │   │   ├── config/
│   │   │   │   └── WebSecurityConfig.java       # Security configuration
│   │   │   ├── controller/
│   │   │   │   └── QuizController.java          # Main controller
│   │   │   ├── model/
│   │   │   │   ├── Question.java                # Question entity
│   │   │   │   └── User.java                    # User entity
│   │   │   ├── service/
│   │   │   │   ├── QuestionService.java         # Question management
│   │   │   │   └── QuizUserDetailsService.java  # User authentication
│   │   │   └── QuizApplication.java             # Main application class
│   │   └── resources/
│   │       ├── templates/                        # Thymeleaf templates
│   │       │   ├── login.html
│   │       │   ├── register.html
│   │       │   ├── quiz.html
│   │       │   ├── quizlist.html
│   │       │   ├── addquiz.html
│   │       │   ├── editquiz.html
│   │       │   └── result.html
│   │       ├── static/css/                       # CSS stylesheets
│   │       └── application.properties            # Application configuration
│   └── test/
│       └── java/com/quiz/
│           └── QuizApplicationTests.java         # Test cases
├── pom.xml                                       # Maven dependencies
└── README.md                                     # This file
```

## 🧪 Testing

Run the test suite:

```bash
./mvnw test
```

## 🔄 API Endpoints

### Public Endpoints
- `GET /` - Redirect to login page
- `GET /login` - Login page
- `POST /login` - Process login
- `GET /register` - Registration page
- `POST /register` - Process registration

### User Endpoints (Authenticated)
- `GET /home` - Home page (redirects based on role)
- `GET /quiz` - Take quiz page
- `POST /quiz` - Submit quiz answers

### Admin Endpoints (Admin Role Required)
- `GET /quizlist` - View all questions
- `GET /addquiz` - Add question form
- `POST /addquiz` - Create new question
- `GET /editquiz/{id}` - Edit question form
- `PUT /editquiz` - Update question
- `DELETE /question/delete/{id}` - Delete question

## 📝 Configuration

The application uses default configuration. Key settings in `application.properties`:

```properties
spring.application.name=quiz
spring.mvc.hiddenmethod.filter.enabled=true
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is open source and available under the MIT License.

## 👤 Author

**Kasra Saber Tehrani**
- GitHub: [@kasrasabertehrani](https://github.com/kasrasabertehrani)

## 🙏 Acknowledgments

- Spring Boot Team for the excellent framework
- Spring Security for robust security features
- Thymeleaf for powerful templating

## 📧 Contact

For questions or feedback, please open an issue on GitHub.

---

**Note**: This application currently uses in-memory storage (HashMap) for demonstration purposes. For production use, consider integrating a database (MySQL, PostgreSQL, etc.) with Spring Data JPA.
