# 🚀 QUICK START GUIDE

## Before Running - IMPORTANT!

### 1. Start PostgreSQL
Make sure PostgreSQL is running on your computer.

### 2. Create Database
Open PostgreSQL terminal (pgAdmin or psql) and run:
```sql
CREATE DATABASE student_mgmt;
```

### 3. Check Database Password
Open `application.properties` and verify the password:
```properties
spring.datasource.password=2021
```
If your PostgreSQL password is different, change it!

---

## Running the Application

### Option 1: Using Maven (Recommended)
```bash
.\mvnw.cmd spring-boot:run
```

### Option 2: Using JAR file
```bash
java -jar target\demo-0.0.1-SNAPSHOT.jar
```

---

## Access the Application

Open your browser: **http://localhost:8080**

---

## First Time Usage

### Step 1: Register as Teacher
1. Click "Register as Teacher"
2. Enter:
   - Name: `John`
   - Department: Select `CSE`
   - Password: `password`
3. Click "Register"

### Step 2: Login as Teacher
1. Username: `John`
2. Password: `password`
3. Click "Login"

### Step 3: Add a Course
1. Click "Add New Course"
2. Enter:
   - Course Name: `Data Structures`
   - Course Code: `CSE201`
   - Department: Select `CSE`
3. Click "Add Course"

### Step 4: Add a Student
1. Click "Add New Student"
2. Enter:
   - Name: `Jane Doe`
   - Roll Number: `2021001`
   - Department: Select `CSE`
   - Select Courses: Check `Data Structures`
   - Password: `student123`
3. Click "Add Student"

### Step 5: Logout

### Step 6: Login as Student
1. Username: `2021001` (roll number)
2. Password: `student123`
3. Click "Login"

### Step 7: Edit Student Profile
1. Click "Edit Profile"
2. Try to change name, department, courses
3. **Note**: Roll number is disabled (cannot be changed) ✅
4. Click "Update Profile"

---

## Troubleshooting

### Port 8080 already in use
```bash
# Kill the process using port 8080
netstat -ano | findstr :8080
taskkill /PID <PID_NUMBER> /F
```

### Database connection error
- Check if PostgreSQL is running
- Verify database name is `student_mgmt`
- Check username (default: `postgres`)
- Check password in `application.properties`

### Cannot login
- Teachers login with **name**
- Students login with **roll number**

---

## System is Ready! ✅

All features are working:
- ✅ Teacher registration
- ✅ Teacher can add courses
- ✅ Teacher can add students
- ✅ Student can login
- ✅ Student can edit profile (except roll number)
- ✅ All data visible in interface
- ✅ Authentication & Authorization working
- ✅ 4 Entities with proper relationships

**Your homework is complete!** 🎉
