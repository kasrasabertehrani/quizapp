# Contributing to Quiz Application

Thank you for your interest in contributing to the Quiz Application! This document provides guidelines and instructions for contributing to this project.

## Code of Conduct

- Be respectful and inclusive
- Provide constructive feedback
- Focus on the project goals and quality

## How to Contribute

### Reporting Bugs

If you find a bug, please create an issue with:
- A clear title and description
- Steps to reproduce the issue
- Expected vs actual behavior
- Environment details (OS, Java version, etc.)
- Screenshots if applicable

### Suggesting Enhancements

We welcome suggestions! Please create an issue with:
- A clear title and description
- Use case and benefits
- Possible implementation approach
- Any mockups or examples

### Pull Requests

1. **Fork the repository**
   ```bash
   git clone https://github.com/kasrasabertehrani/quizapp.git
   cd quizapp
   ```

2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make your changes**
   - Follow the existing code style
   - Add tests for new functionality
   - Update documentation as needed
   - Ensure all tests pass

4. **Commit your changes**
   ```bash
   git add .
   git commit -m "Add: brief description of changes"
   ```

5. **Push to your fork**
   ```bash
   git push origin feature/your-feature-name
   ```

6. **Create a Pull Request**
   - Provide a clear description of changes
   - Reference any related issues
   - Ensure CI/CD checks pass

## Development Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Git

### Local Development

1. **Clone and build**
   ```bash
   git clone https://github.com/kasrasabertehrani/quizapp.git
   cd quizapp
   ./mvnw clean install
   ```

2. **Run tests**
   ```bash
   ./mvnw test
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the application**
   - URL: http://localhost:8080
   - Register a new user to get started

## Code Style Guidelines

### Java
- Follow standard Java naming conventions
- Use meaningful variable and method names
- Add Javadoc comments for public methods
- Keep methods small and focused
- Maximum line length: 120 characters

### Security
- Never commit sensitive information (passwords, API keys)
- Always validate user input
- Use parameterized queries (when database is added)
- Follow OWASP security best practices

### Testing
- Write unit tests for new functionality
- Maintain test coverage above 70%
- Use meaningful test names
- Test edge cases and error conditions

## Project Structure

```
quizapp/
├── src/
│   ├── main/
│   │   ├── java/com/quiz/
│   │   │   ├── config/          # Security and app configuration
│   │   │   ├── controller/      # Web controllers
│   │   │   ├── exception/       # Exception handlers
│   │   │   ├── model/           # Domain models
│   │   │   └── service/         # Business logic
│   │   └── resources/
│   │       ├── templates/       # Thymeleaf templates
│   │       ├── static/          # CSS, JS, images
│   │       └── application.properties
│   └── test/                    # Test files
├── .github/workflows/           # CI/CD configuration
├── Dockerfile                   # Docker configuration
└── pom.xml                      # Maven dependencies
```

## Testing Your Changes

Before submitting a pull request:

1. **Run all tests**
   ```bash
   ./mvnw clean test
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```

3. **Test manually**
   - Start the application
   - Test the affected features
   - Check for errors in logs

## Commit Message Guidelines

Use clear, descriptive commit messages:

- `Add: new feature or file`
- `Update: changes to existing functionality`
- `Fix: bug fixes`
- `Refactor: code improvements without functionality changes`
- `Docs: documentation changes`
- `Test: adding or updating tests`

Example:
```
Add: user profile management feature

- Added profile view page
- Added profile edit functionality
- Added tests for profile service
```

## Questions?

If you have questions:
- Check existing issues and pull requests
- Create a new issue with the `question` label
- Be specific about what you need help with

## License

By contributing, you agree that your contributions will be licensed under the same license as the project (MIT License).

Thank you for contributing to the Quiz Application! 🎉
