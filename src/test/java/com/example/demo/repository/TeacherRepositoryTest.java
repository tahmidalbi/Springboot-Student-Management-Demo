package com.example.demo.repository;

import com.example.demo.entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherRepositoryTest {

    @Mock
    private TeacherRepository teacherRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("findByName returns teacher when found")
    void findByName() {
        // Arrange
        Teacher t = new Teacher();
        t.setName("John");

        when(teacherRepository.findByName("John")).thenReturn(Optional.of(t));
        when(teacherRepository.findByName("Missing")).thenReturn(Optional.empty());

        // Act
        Optional<Teacher> found = teacherRepository.findByName("John");
        Optional<Teacher> missing = teacherRepository.findByName("Missing");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("John", found.get().getName());

        assertTrue(missing.isEmpty());

        verify(teacherRepository, times(1)).findByName("John");
        verify(teacherRepository, times(1)).findByName("Missing");
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    @DisplayName("existsByName returns true when exists and false when not")
    void existsByName() {
        // Arrange
        when(teacherRepository.existsByName("Alice")).thenReturn(true);
        when(teacherRepository.existsByName("Bob")).thenReturn(false);

        // Act + Assert
        assertTrue(teacherRepository.existsByName("Alice"));
        assertFalse(teacherRepository.existsByName("Bob"));

        verify(teacherRepository, times(1)).existsByName("Alice");
        verify(teacherRepository, times(1)).existsByName("Bob");
        verifyNoMoreInteractions(teacherRepository);
    }
}
