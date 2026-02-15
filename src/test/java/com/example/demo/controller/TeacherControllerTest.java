package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.Department;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;
import com.example.demo.service.CourseService;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.StudentService;
import com.example.demo.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherControllerTest {

    private TeacherController controller;

    @Mock private TeacherService teacherService;
    @Mock private DepartmentService departmentService;
    @Mock private CourseService courseService;
    @Mock private StudentService studentService;
    @Mock private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        controller = new TeacherController();

        // Inject mocks into the controller fields (because controller uses @Autowired fields)
        setField(controller, "teacherService", teacherService);
        setField(controller, "departmentService", departmentService);
        setField(controller, "courseService", courseService);
        setField(controller, "studentService", studentService);
        setField(controller, "passwordEncoder", passwordEncoder);
    }

    // Utility to set private fields via reflection
    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }

    @Test
    void showRegisterForm() {
        // Arrange
        List<Department> depts = Arrays.asList(new Department(), new Department());
        when(departmentService.findAll()).thenReturn(depts);

        Model model = new ExtendedModelMap();

        // Act
        String viewName = controller.showRegisterForm(model);

        // Assert
        assertEquals("teacher/register", viewName);

        assertNotNull(model.getAttribute("teacher"), "teacher attribute should exist");
        assertTrue(model.getAttribute("teacher") instanceof Teacher);

        assertSame(depts, model.getAttribute("departments"), "departments attribute should be what service returned");

        verify(departmentService, times(1)).findAll();
        verifyNoMoreInteractions(departmentService);
        verifyNoInteractions(teacherService, courseService, studentService, passwordEncoder);
    }

    @Test
    void deleteCourse() {
        // Arrange
        Long courseId = 10L;
        RedirectAttributes ra = new RedirectAttributesModelMap();

        // Act
        String viewName = controller.deleteCourse(courseId, ra);

        // Assert
        assertEquals("redirect:/teacher/dashboard", viewName);
        assertEquals("Course deleted successfully!", ra.getFlashAttributes().get("success"));

        verify(courseService, times(1)).delete(courseId);
        verifyNoMoreInteractions(courseService);
        verifyNoInteractions(teacherService, departmentService, studentService, passwordEncoder);
    }

    @Test
    void showAddStudentForm() {
        // Arrange
        List<Department> depts = Arrays.asList(new Department(), new Department());
        List<Course> courses = Arrays.asList(new Course(), new Course(), new Course());

        when(departmentService.findAll()).thenReturn(depts);
        when(courseService.findAll()).thenReturn(courses);

        Model model = new ExtendedModelMap();

        // Act
        String viewName = controller.showAddStudentForm(model);

        // Assert
        assertEquals("teacher/add-student", viewName);

        assertNotNull(model.getAttribute("student"));
        assertTrue(model.getAttribute("student") instanceof Student);

        assertSame(depts, model.getAttribute("departments"));
        assertSame(courses, model.getAttribute("courses"));

        verify(departmentService, times(1)).findAll();
        verify(courseService, times(1)).findAll();
        verifyNoMoreInteractions(departmentService, courseService);
        verifyNoInteractions(teacherService, studentService, passwordEncoder);
    }

    @Test
    void deleteStudent() {
        // Arrange
        Long studentId = 7L;
        RedirectAttributes ra = new RedirectAttributesModelMap();

        // Act
        String viewName = controller.deleteStudent(studentId, ra);

        // Assert
        assertEquals("redirect:/teacher/dashboard", viewName);
        assertEquals("Student deleted successfully!", ra.getFlashAttributes().get("success"));

        verify(studentService, times(1)).delete(studentId);
        verifyNoMoreInteractions(studentService);
        verifyNoInteractions(teacherService, departmentService, courseService, passwordEncoder);
    }

    @Test
    void viewStudent_whenStudentExists_returnsViewAndAddsModel() {
        // Arrange
        Long studentId = 5L;
        Student s = new Student();
        when(studentService.findById(studentId)).thenReturn(Optional.of(s));
        Model model = new ExtendedModelMap();

        // Act
        String viewName = controller.viewStudent(studentId, model);

        // Assert
        assertEquals("teacher/view-student", viewName);
        assertSame(s, model.getAttribute("student"));

        verify(studentService, times(1)).findById(studentId);
        verifyNoMoreInteractions(studentService);
        verifyNoInteractions(teacherService, departmentService, courseService, passwordEncoder);
    }

    @Test
    void viewStudent_whenStudentMissing_redirectsToDashboard() {
        // Arrange
        Long studentId = 999L;
        when(studentService.findById(studentId)).thenReturn(Optional.empty());
        Model model = new ExtendedModelMap();

        // Act
        String viewName = controller.viewStudent(studentId, model);

        // Assert
        assertEquals("redirect:/teacher/dashboard", viewName);
        assertNull(model.getAttribute("student"), "student attribute should not be set when missing");

        verify(studentService, times(1)).findById(studentId);
        verifyNoMoreInteractions(studentService);
        verifyNoInteractions(teacherService, departmentService, courseService, passwordEncoder);
    }
}
