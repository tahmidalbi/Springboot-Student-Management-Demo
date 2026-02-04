package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.Department;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;
import com.example.demo.service.CourseService;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.StudentService;
import com.example.demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        model.addAttribute("departments", departmentService.findAll());
        return "teacher/register";
    }

    @PostMapping("/register")
    public String registerTeacher(@ModelAttribute Teacher teacher,
                                   @RequestParam Long departmentId,
                                   RedirectAttributes redirectAttributes) {
        if (teacherService.existsByName(teacher.getName())) {
            redirectAttributes.addFlashAttribute("error", "Teacher name already exists!");
            return "redirect:/teacher/register";
        }

        Department dept = departmentService.findById(departmentId).orElse(null);
        if (dept == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid department!");
            return "redirect:/teacher/register";
        }

        teacher.setDepartment(dept);
        teacher.setRole("TEACHER");
        // Encrypt password before saving
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        teacherService.save(teacher);

        redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        String teacherName = auth.getName();
        Teacher teacher = teacherService.findByName(teacherName).orElse(null);

        if (teacher == null) {
            return "redirect:/login";
        }

        List<Course> courses = courseService.findByTeacher(teacher);
        List<Student> students = studentService.findAll();

        model.addAttribute("teacher", teacher);
        model.addAttribute("courses", courses);
        model.addAttribute("students", students);
        model.addAttribute("studentCount", students.size());
        model.addAttribute("courseCount", courses.size());

        return "teacher/dashboard";
    }

    // Course Management
    @GetMapping("/courses/add")
    public String showAddCourseForm(Authentication auth, Model model) {
        String teacherName = auth.getName();
        Teacher teacher = teacherService.findByName(teacherName).orElse(null);

        model.addAttribute("course", new Course());
        model.addAttribute("teacher", teacher);
        model.addAttribute("departments", departmentService.findAll());
        return "teacher/add-course";
    }

    @PostMapping("/courses/add")
    public String addCourse(@ModelAttribute Course course,
                            @RequestParam Long departmentId,
                            Authentication auth,
                            RedirectAttributes redirectAttributes) {
        String teacherName = auth.getName();
        Teacher teacher = teacherService.findByName(teacherName).orElse(null);

        if (teacher == null) {
            return "redirect:/login";
        }

        Department dept = departmentService.findById(departmentId).orElse(null);
        course.setDepartment(dept);
        course.setTeacher(teacher);
        courseService.save(course);

        redirectAttributes.addFlashAttribute("success", "Course added successfully!");
        return "redirect:/teacher/dashboard";
    }

    @GetMapping("/courses/{id}/delete")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Course deleted successfully!");
        return "redirect:/teacher/dashboard";
    }

    // Student Management
    @GetMapping("/students/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("courses", courseService.findAll());
        return "teacher/add-student";
    }

    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute Student student,
                             @RequestParam Long departmentId,
                             @RequestParam(required = false) List<Long> courseIds,
                             RedirectAttributes redirectAttributes) {
        if (studentService.existsByRollNo(student.getRollNo())) {
            redirectAttributes.addFlashAttribute("error", "Roll number already exists!");
            return "redirect:/teacher/students/add";
        }

        Department dept = departmentService.findById(departmentId).orElse(null);
        student.setDepartment(dept);
        student.setRole("STUDENT");
        // Encrypt password before saving
        student.setPassword(passwordEncoder.encode(student.getPassword()));

        // Add courses
        Set<Course> courses = new HashSet<>();
        if (courseIds != null) {
            for (Long courseId : courseIds) {
                courseService.findById(courseId).ifPresent(course -> {
                    courses.add(course);
                });
            }
        }
        student.setCourses(courses);

        studentService.save(student);

        redirectAttributes.addFlashAttribute("success", "Student added successfully!");
        return "redirect:/teacher/dashboard";
    }

    @GetMapping("/students/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        return "redirect:/teacher/dashboard";
    }

    @GetMapping("/students/{id}/view")
    public String viewStudent(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id).orElse(null);
        if (student == null) {
            return "redirect:/teacher/dashboard";
        }
        model.addAttribute("student", student);
        return "teacher/view-student";
    }
}
