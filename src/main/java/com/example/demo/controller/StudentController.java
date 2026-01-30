package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.Department;
import com.example.demo.entity.Student;
import com.example.demo.service.CourseService;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.StudentService;
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
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        String rollNo = auth.getName();
        Student student = studentService.findByRollNo(rollNo).orElse(null);

        if (student == null) {
            return "redirect:/login";
        }

        model.addAttribute("student", student);
        return "student/dashboard";
    }

    @GetMapping("/profile")
    public String viewProfile(Authentication auth, Model model) {
        String rollNo = auth.getName();
        Student student = studentService.findByRollNo(rollNo).orElse(null);

        if (student == null) {
            return "redirect:/login";
        }

        model.addAttribute("student", student);
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("allCourses", courseService.findAll());
        return "student/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute Student updatedStudent,
                                @RequestParam Long departmentId,
                                @RequestParam(required = false) List<Long> courseIds,
                                Authentication auth,
                                RedirectAttributes redirectAttributes) {
        String rollNo = auth.getName();
        Student existingStudent = studentService.findByRollNo(rollNo).orElse(null);

        if (existingStudent == null) {
            return "redirect:/login";
        }

        // Update fields (but not roll number!)
        existingStudent.setName(updatedStudent.getName());
        existingStudent.setPassword(updatedStudent.getPassword());

        Department dept = departmentService.findById(departmentId).orElse(null);
        existingStudent.setDepartment(dept);

        // Update courses
        Set<Course> courses = new HashSet<>();
        if (courseIds != null) {
            for (Long courseId : courseIds) {
                courseService.findById(courseId).ifPresent(course -> {
                    courses.add(course);
                });
            }
        }
        existingStudent.setCourses(courses);

        studentService.save(existingStudent);

        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/student/profile";
    }
}
