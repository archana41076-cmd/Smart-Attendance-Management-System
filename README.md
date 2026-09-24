##Smart Attendance Management System

A web-based attendance management system designed to help colleges manage student attendance efficiently through a simple and user-friendly interface.
The system provides functionality for managing students, subjects, attendance records, attendance summaries, and identifying students with low attendance.


## Problem Statement

A college may have thousands of students, hundreds of faculty members, multiple departments, sections, and subjects. Managing attendance manually can become time-consuming and difficult to maintain.
This project provides a centralized system to record and monitor student attendance using a Spring Boot REST API, database, and a simple web-based frontend.


## Features

- Student management
- Subject management
- Mark attendance for students
- Attendance status:
  - Present
  - Absent
  - On Leave
- View attendance records by subject
- View individual student attendance summary
- Calculate attendance percentage
- Identify students below a specified attendance threshold
- Dashboard with attendance statistics
- REST API-based communication between frontend and backend
- Basic validation and error handling


## Technology Stack

### Frontend
- HTML5
- CSS3
- JavaScript

### Backend
- Java
- Spring Boot
- Spring Data JPA
- REST APIs

### Database
- PostgreSQL

### Development & Testing Tools
- Eclipse IDE
- Postman
- Git
- GitHub



## System Architecture

The application follows a simple layered architecture where the frontend communicates with the Spring Boot backend through REST APIs.


+-----------------------------+
|          Frontend           |
|     HTML + CSS + JavaScript |
+--------------+--------------+
               |
               | REST API
               v
+-----------------------------+
|          Backend            |
|         Spring Boot         |
|                             |
| Controller → Service → Repo  |
+--------------+--------------+
               |
               | JPA / Hibernate
               v
+-----------------------------+
|          Database           |
|         PostgreSQL          |
+-----------------------------+


##Architecture Flow

1.The user interacts with the web interface.
2.JavaScript sends requests to the Spring Boot REST APIs.
3.Controllers receive and process the requests.
4.The service layer handles the application logic.
5.Repository classes communicate with the database.
6.The response is returned to the frontend and displayed to the user.


## Main Modules

### 1. Dashboard
The dashboard provides a quick overview of attendance information, including:

- Total Students
- Attendance Records
- Present Students
- Absent Students

### 2. Student Management
Student information can be stored and retrieved through REST APIs.
Student details include:

- Student Name
- Roll Number
- Department
- Section
- Email

### 3. Subject Management
Subject information includes:

- Subject Name
- Subject Code
- Department
- Section
- Faculty Name

### 4. Mark Attendance
The user can select a subject and attendance date, load the students, and mark each student as:

- Present
- Absent
- On Leave

### 5. Attendance Records
Attendance records can be retrieved for a selected subject and displayed in a table containing:

- Student Name
- Roll Number
- Subject
- Date
- Attendance Status

### 6. Attendance Summary
The system provides an attendance summary for a selected student and subject.
The summary includes:

- Total Classes
- Present Classes
- Absent Classes
- Attendance Percentage

### 7. Low Attendance
The user can provide an attendance threshold, such as 75%.
The system identifies students whose attendance percentage is below the specified threshold.


## API Overview
The frontend communicates with the Spring Boot backend using REST APIs.
The main APIs used in the application are:

- `GET /api/students` - Retrieve all students
- `POST /api/students` - Add a new student
- `GET /api/attendance/subject/{subjectId}` - Retrieve attendance records for a subject
- `POST /api/attendance/mark/{subjectId}?date={date}` - Mark attendance
- `GET /api/attendance/summary` - Retrieve student attendance summary
- `GET /api/attendance/low-attendance/{subjectId}` - Find students with low attendance

The APIs were tested using Postman during development.


## Database Design
The application uses a relational database to store student, subject, and attendance information.

### Student
Stores:
- Student ID
- Student Name
- Roll Number
- Department
- Section
- Email

### Subject
Stores:
- Subject ID
- Subject Name
- Subject Code
- Department
- Section
- Faculty Name

### Attendance
Stores:
- Attendance ID
- Student
- Subject
- Attendance Date
- Attendance Status
The attendance entity connects students and subjects and stores the attendance status for a particular date.



## Approach
The application was developed using a database-driven approach.
First, the required entities for students, subjects, and attendance were created. REST APIs were implemented using Spring Boot to perform operations such as retrieving students, adding subjects, marking attendance, retrieving attendance records, and calculating attendance percentages.
A simple HTML, CSS, and JavaScript frontend was created to consume these APIs and provide an easy-to-use interface.

Sample data is used for demonstration and testing. The database-driven design allows the system to be extended to support a larger number of students and attendance records.

---

## Assumptions

- Each student has a unique student ID and roll number.
- Students belong to a department and section.
- Subjects are associated with a department and section.
- Attendance is recorded for a specific student, subject, and date.
- Attendance status can be Present, Absent, or On Leave.
- Attendance percentage is calculated using the available attendance records.
- Sample data is used for demonstration instead of manually entering thousands of student records.
- The current application is a prototype that can be extended with additional features.


## Validation and Edge Cases
The application handles the following basic validations and edge cases:
- Subject ID is required before loading students.
- Attendance date is required before submitting attendance.
- Student ID and Subject ID are required for attendance summary.
- Attendance threshold is restricted to a valid percentage range.
- Empty student lists are handled.
- Empty attendance records are handled.
- API failures are handled with error messages.
- Invalid or unavailable backend responses are handled by the frontend.
- The system displays a message when no students have low attendance.


## Testing
The application was tested using Postman and the frontend application.

### API Testing
The following operations were tested using Postman:
- Creating student records
- Creating subject records
- Retrieving student records
- Marking attendance
- Retrieving attendance records
- Checking attendance summary
- Checking low attendance students

### Frontend Testing

The following workflows were tested:
1. Loading students.
2. Selecting attendance status.
3. Submitting attendance.
4. Viewing attendance records.
5. Checking student attendance summary.
6. Finding students with low attendance.
7. Verifying dashboard statistics.


## Sample Data
Sample data is used for demonstration and testing.
The current dataset includes:
- 10 students
- 5 subjects
- Attendance records for testing different attendance statuses
The problem statement mentions approximately 5,000 students. It is not necessary to manually create 5,000 records for the prototype. The sample data is used to demonstrate the functionality, while the database-driven design allows the application to be extended to larger datasets.


## Trade-offs
The following design decisions were made for this prototype:
- Plain HTML, CSS, and JavaScript were used for the frontend to keep the application simple and lightweight.
- Spring Boot REST APIs were used to separate frontend and backend responsibilities.
- A relational database was used to maintain structured student, subject, and attendance data.
- Sample data was used instead of manually creating thousands of records.
- The prototype focuses on the core attendance workflow rather than advanced authentication and reporting features.


## Limitations
The current prototype does not include:
- User authentication
- Role-based access control
- Faculty login
- Admin dashboard
- Attendance export to Excel or PDF
- Email notifications
- Advanced reporting
- Pagination for large datasets
These features can be added in future versions.


## Future Enhancements
Possible future improvements include:
- Admin and faculty authentication
- Role-based access control
- Department-wise attendance reports
- Search and pagination for large student datasets
- Export attendance reports to Excel or PDF
- Email notifications for low attendance
- Advanced dashboard analytics
- Attendance filtering by date, department, section, and subject


## How to Run the Project
### Backend
1. Open the Spring Boot project in Eclipse.
2. Configure the database connection.
3. Run the Spring Boot application.
4. Make sure the backend is running on the configured port.

For example:
http://localhost:8080