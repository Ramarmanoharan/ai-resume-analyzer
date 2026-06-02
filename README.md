# AI Resume Analyzer

## Overview
AI Resume Analyzer is a full-stack web application that analyzes resumes against job descriptions. It calculates an ATS score, identifies matched and missing skills, generates interview questions, and stores analysis history for future reference.

## Features
- Resume PDF Upload
- ATS Score Calculation
- Skill Match Analysis
- Missing Keyword Detection
- Interview Question Generation
- Analysis History Tracking
- MySQL Database Integration
- Responsive UI

## Tech Stack

### Frontend
- HTML5
- CSS3
- JavaScript
- Bootstrap
- Thymeleaf

### Backend
- Java
- Spring Boot
- Spring MVC
- Spring Data JPA

### Database
- MySQL

### Libraries
- Apache PDFBox
- Lombok

## Project Structure

```text
src/
 ├── main/
 │   ├── java/
 │   │   └── com/ramar/resumeanalyzer
 │   ├── resources/
 │   │   ├── templates/
 │   │   ├── static/
 │   │   └── application.properties
 └── test/
```

## Installation

1. Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/AI-Resume-Analyzer.git
```

2. Open Project

```bash
cd AI-Resume-Analyzer
```

3. Configure MySQL

Create database:

```sql
CREATE DATABASE resume_analyzer_db;
```

4. Update application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/resume_analyzer_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

5. Run Project

```bash
./mvnw spring-boot:run
```

## Screenshots

### Home Page
- Resume Upload
- Job Description Input
- ATS Analysis

### Result Page
- ATS Score
- Matched Skills
- Missing Skills
- Interview Questions

## Future Enhancements
- AI-based Resume Suggestions
- GPT-powered Career Guidance
- Resume Ranking System
- Job Recommendation Engine
- Email Report Generation

## Author

**Ramar M**

- Full Stack Java Developer
- CSE (AI & DS)

## License

This project is developed for educational and portfolio purposes.
