# Security Policy

## Overview

The Quiz Application implements multiple security layers using Spring Security 6 to protect user data and prevent common web vulnerabilities.

## Security Features

### 1. Authentication

- **Form-Based Authentication**: Custom login page with Spring Security
- **Password Encryption**: BCrypt hashing with salt (work factor: 10)
- **Session Management**: 
  - Maximum one session per user
  - Session invalidation on logout
  - JSESSIONID cookie cleared on logout

### 2. Authorization

- **Role-Based Access Control (RBAC)**:
  - `USER` role: Can take quizzes and view results
  - `ADMIN` role: Can manage quiz questions (CRUD operations)
  
- **Endpoint Protection**:
  ```
  Public: /login, /register, /css/**
  USER:   /quiz, /home
  ADMIN:  /quizlist, /addquiz, /editquiz/**, /question/delete/**
  ```

### 3. CSRF Protection

- **Enabled by default**: All state-changing operations require CSRF token
- **Token inclusion**: Automatically included in Thymeleaf forms
- **Token validation**: Validated on server-side for POST/PUT/DELETE requests

### 4. Security Headers

- **Content Security Policy (CSP)**:
  ```
  default-src 'self'
  style-src 'self' 'unsafe-inline'
  script-src 'self' 'unsafe-inline'
  ```
  
- **X-Frame-Options**: DENY (prevents clickjacking)
- **X-Content-Type-Options**: nosniff
- **X-XSS-Protection**: 1; mode=block

### 5. Input Validation

- **Jakarta Validation** annotations on model classes
- **Username**: 3-50 characters, required
- **Password**: Minimum 8 characters, required
- **Email**: Valid email format, required
- **Question Text**: 10-500 characters
- **Options**: 2-10 options per question

### 6. Error Handling

- **Global Exception Handler**: Centralized error handling
- **Custom Error Pages**: User-friendly error messages
- **Security Error Logging**: Failed authentication attempts logged
- **No Stack Traces**: Error details not exposed to users

## Implemented Security Best Practices

### OWASP Top 10 Mitigations

1. **Injection Prevention**
   - No SQL database (currently using in-memory storage)
   - Input validation on all user inputs
   - Parameterized data handling

2. **Broken Authentication**
   - Strong password hashing (BCrypt)
   - Session timeout configuration
   - Secure session management

3. **Sensitive Data Exposure**
   - Passwords never stored in plain text
   - No sensitive data in logs
   - Secure password transmission (HTTPS recommended)

4. **XML External Entities (XXE)**
   - Not applicable (no XML processing)

5. **Broken Access Control**
   - Role-based authorization
   - Method-level security
   - URL pattern matching

6. **Security Misconfiguration**
   - Security headers configured
   - Default credentials not used
   - Minimal information disclosure

7. **Cross-Site Scripting (XSS)**
   - Thymeleaf automatic escaping
   - Content Security Policy
   - Input validation

8. **Insecure Deserialization**
   - Not applicable (no object deserialization)

9. **Using Components with Known Vulnerabilities**
   - CI/CD pipeline with dependency checks
   - Regular dependency updates
   - OWASP Dependency Check in build process

10. **Insufficient Logging & Monitoring**
    - SLF4J logging framework
    - Authentication events logged
    - Error tracking with stack traces

## Security Configuration

### WebSecurityConfig.java

Key configurations:
- Custom UserDetailsService
- BCrypt password encoder
- HTTP security rules
- Session management
- CSRF protection
- Security headers

### Example Security Test

```java
@Test
void testPasswordEncryption() {
    String plainPassword = "mypassword";
    userDetailsService.registerUser("user", plainPassword, "USER", "user@test.com");
    
    UserDetails user = userDetailsService.loadUserByUsername("user");
    
    // Password should be hashed
    assertTrue(user.getPassword().startsWith("$2"));
    assertNotEquals(plainPassword, user.getPassword());
}
```

## Deployment Security

### Production Recommendations

1. **Enable HTTPS**
   - Use TLS 1.2 or higher
   - Redirect HTTP to HTTPS
   - Use HSTS header

2. **Environment Variables**
   - Store secrets in environment variables
   - Never commit secrets to version control
   - Use secret management tools

3. **Database Security** (when implemented)
   - Use connection pooling
   - Encrypt database connections
   - Regular backups
   - Principle of least privilege for DB users

4. **Monitoring**
   - Set up security monitoring
   - Track failed login attempts
   - Alert on suspicious activity
   - Regular security audits

5. **Updates**
   - Keep dependencies up to date
   - Subscribe to security advisories
   - Regular security patches

## Reporting Security Vulnerabilities

If you discover a security vulnerability:

1. **DO NOT** create a public issue
2. Email the maintainer directly (see GitHub profile)
3. Include:
   - Description of the vulnerability
   - Steps to reproduce
   - Potential impact
   - Suggested fix (if available)

We will respond within 48 hours and work on a fix.

## Security Checklist for Contributors

Before submitting code:

- [ ] No hardcoded secrets or passwords
- [ ] User input is validated
- [ ] Authorization checks are in place
- [ ] CSRF protection is maintained
- [ ] No sensitive data in logs
- [ ] Tests include security scenarios
- [ ] Dependencies are up to date
- [ ] Security headers are not modified

## References

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [OWASP Cheat Sheet Series](https://cheatsheetseries.owasp.org/)
- [BCrypt Information](https://en.wikipedia.org/wiki/Bcrypt)

## Version

This security policy applies to version 1.0.0 and later of the Quiz Application.

Last Updated: 2026-01-29
