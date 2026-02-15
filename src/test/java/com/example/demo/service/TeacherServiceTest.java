package com.example.demo.service;

import com.example.demo.entity.Teacher;
import com.example.demo.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setName("John");

        when(teacherRepository.save(teacher)).thenReturn(teacher);

        // Act
        Teacher result = teacherService.save(teacher);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getName());

        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    void findAll() {
        // Arrange
        Teacher t1 = new Teacher();
        t1.setName("John");

        Teacher t2 = new Teacher();
        t2.setName("Alice");

        List<Teacher> teachers = Arrays.asList(t1, t2);

        when(teacherRepository.findAll()).thenReturn(teachers);

        // Act
        List<Teacher> result = teacherService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Alice", result.get(1).getName());

        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("John");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        Optional<Teacher> found = teacherService.findById(1L);
        Optional<Teacher> notFound = teacherService.findById(2L);

        // Assert
        assertTrue(found.isPresent());
        assertEquals("John", found.get().getName());

        assertTrue(notFound.isEmpty());

        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).findById(2L);
    }

    @Test
    void findByName() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setName("Alice");

        when(teacherRepository.findByName("Alice")).thenReturn(Optional.of(teacher));
        when(teacherRepository.findByName("Bob")).thenReturn(Optional.empty());

        // Act
        Optional<Teacher> found = teacherService.findByName("Alice");
        Optional<Teacher> notFound = teacherService.findByName("Bob");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Alice", found.get().getName());

        assertTrue(notFound.isEmpty());

        verify(teacherRepository, times(1)).findByName("Alice");
        verify(teacherRepository, times(1)).findByName("Bob");
    }

    @Test
    void existsByName() {
        // Arrange
        when(teacherRepository.existsByName("John")).thenReturn(true);
        when(teacherRepository.existsByName("Unknown")).thenReturn(false);

        // Act
        boolean exists = teacherService.existsByName("John");
        boolean notExists = teacherService.existsByName("Unknown");

        // Assert
        assertTrue(exists);
        assertFalse(notExists);

        verify(teacherRepository, times(1)).existsByName("John");
        verify(teacherRepository, times(1)).existsByName("Unknown");
    }
}
