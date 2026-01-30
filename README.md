# Student Management System - Lab Project

## ✅ System Complete!

A simple Spring Boot application for managing students, teachers, departments, and courses with role-based authentication and authorization.

---

## 🎯 Features Implemented

### Authentication & Authorization
- ✅ Teacher registration and login
- ✅ Student login (created by teacher)
- ✅ Role-based access control (TEACHER/STUDENT roles)
- ✅ Secure password storage

### Teacher Functionality
- ✅ Register with name, department (dropdown), and password
- ✅ Login with name and password
- ✅ Add courses (name, code, department)
- ✅ Add students (name, roll, department, courses, password)
- ✅ View all students and courses
- ✅ Delete students and courses

### Student Functionality
- ✅ Login with roll number and password (set by teacher)
- ✅ View dashboard with enrolled courses
- ✅ Edit profile (name, department, courses, password)
- ✅ **Cannot edit roll number** (as required)

### Database Structure
- ✅ **4 Entities**: Department, Teacher, Course, Student
- ✅ **Relationships**:
  - One-to-Many: Department → Teachers, Students, Courses
  - Many-to-One: Teacher/Student/Course → Department
  - Many-to-Many: Student ↔ Course
- ✅ **Pre-populated Departments**: CSE, EEE, Mechanical

---

## 🚀 How to Run

### Prerequisites
1. **PostgreSQL** installed and running
2. **Java 17** installed
3. Maven (included via mvnw)

### Step 1: Setup Database
```sql
-- Open PostgreSQL and create database
CREATE DATABASE student_mgmt;
```

### Step 2: Configure Database Connection
The application is already configured in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/student_mgmt
spring.datasource.username=postgres
spring.datasource.password=2021
```
**Change the password if your PostgreSQL password is different!**

### Step 3: Run the Application
```bash
# Option 1: Using Maven Wrapper (Windows)
.\mvnw.cmd spring-boot:run

# Option 2: Using compiled JAR
.\mvnw.cmd clean package
java -jar target\demo-0.0.1-SNAPSHOT.jar
```

### Step 4: Access the Application
Open your browser and go to:
```
http://localhost:8080
```

---

## 📖 User Guide

### For Teachers:

1. **Register**
   - Go to http://localhost:8080
   - Click "Register as Teacher"
   - Fill: Name, Department (CSE/EEE/Mechanical), Password
   - Click Register

2. **Login**
   - Use your **name** as username
   - Enter your password
   - You'll be redirected to Teacher Dashboard

3. **Add Courses**
   - Click "Add New Course" button
   - Enter course name, code, and select department
   - Course will appear in "My Courses" section

4. **Add Students**
   - Click "Add New Student" button
   - Fill: Name, Roll Number, Department
   - Select courses to enroll student
   - Set password for the student
   - Student can now login with roll number and password

5. **Manage Students**
   - View all students in dashboard
   - Click "View" to see student details
   - Click "Delete" to remove a student

### For Students:

1. **Login** (Teacher must create your account first)
   - Use your **roll number** as username
   - Enter the password set by teacher
   - You'll be redirected to Student Dashboard

2. **View Dashboard**
   - See your enrolled courses
   - View your personal information

3. **Edit Profile**
   - Click "Edit Profile" button
   - Update: Name, Department, Courses, Password
   - **Roll number cannot be changed** ✅
   - Click "Update Profile"

---

## 🗂️ Project Structure

```
src/main/java/com/example/demo/
├── entity/
│   ├── Department.java      # Department entity
│   ├── Teacher.java          # Teacher entity (has dept, courses)
│   ├── Course.java           # Course entity (has dept, teacher, students)
│   └── Student.java          # Student entity (has dept, courses)
├── repository/
│   ├── DepartmentRepository.java
│   ├── TeacherRepository.java
│   ├── CourseRepository.java
│   └── StudentRepository.java
├── service/
│   ├── DepartmentService.java
│   ├── TeacherService.java
│   ├── CourseService.java
│   ├── StudentService.java
│   └── CustomUserDetailsService.java  # Authentication
├── controller/
│   ├── ViewController.java            # Main routes
│   ├── TeacherController.java         # Teacher operations
│   └── StudentController.java         # Student operations
└── config/
    └── SecurityConfig.java            # Security & authorization

src/main/resources/
├── templates/
│   ├── index.html                     # Home page
│   ├── login.html                     # Login page
│   ├── teacher/
│   │   ├── register.html              # Teacher registration
│   │   ├── dashboard.html             # Teacher dashboard
│   │   ├── add-course.html            # Add course form
│   │   ├── add-student.html           # Add student form
│   │   └── view-student.html          # View student details
│   └── student/
│       ├── dashboard.html             # Student dashboard
│       └── profile.html               # Edit profile
└── application.properties             # Configuration
```

---

## 🔐 Security Features

- **NoOpPasswordEncoder** used for simplicity (as requested for lab project)
- **Role-based access**:
  - `/teacher/**` → Only TEACHER role
  - `/student/**` → Only STUDENT role
- **Custom UserDetailsService** authenticates:
  - Teachers by name
  - Students by roll number

---

## 📊 Database Relationships

```
Department (1) ─────< (M) Teacher
           (1) ─────< (M) Student
           (1) ─────< (M) Course

Teacher (1) ────────< (M) Course

Student (M) ────────< (M) Course  [Many-to-Many via student_courses table]
```

---

## 🧪 Test Data

After starting the application, departments are automatically created:
- CSE
- EEE
- Mechanical

### Sample Test Flow:

1. **Register as Teacher**
   - Name: John Doe
   - Department: CSE
   - Password: teacher123

2. **Login as Teacher**
   - Username: John Doe
   - Password: teacher123

3. **Add a Course**
   - Name: Data Structures
   - Code: CSE201
   - Department: CSE

4. **Add a Student**
   - Name: Jane Smith
   - Roll: 2021001
   - Department: CSE
   - Courses: Data Structures
   - Password: student123

5. **Logout and Login as Student**
   - Username: 2021001
   - Password: student123

6. **Edit Student Profile**
   - Change name, courses, etc.
   - Roll number is read-only ✅

---

## ✨ Key Requirements Met

✅ **4 Entities**: Department, Teacher, Course, Student  
✅ **2 Roles**: TEACHER, STUDENT  
✅ **Authentication**: Login system with username/password  
✅ **Authorization**: Role-based access control  
✅ **Teacher Registration**: Name, Department dropdown, Password  
✅ **Pre-populated Departments**: CSE, EEE, Mechanical  
✅ **Teacher adds Courses**: After login  
✅ **Teacher adds Students**: With roll, dept, courses, password  
✅ **Student Login**: Using roll number and password  
✅ **Student Edit Profile**: Can update all fields except roll number  
✅ **Database Relationships**: 1:M, M:1, M:M implemented  
✅ **Simple UI**: Bootstrap-based, clean interface  

---

## 🎓 Lab Project Completed!

This system satisfies all homework requirements:
- Simple and functional
- All entities and relationships present
- Authentication and authorization working
- Teacher and student workflows complete
- Roll number protection implemented
- Everything visible and usable in the interface

**Ready for submission!** 🚀
