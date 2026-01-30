package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.entity.Teacher;
import com.example.demo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> findByTeacher(Teacher teacher) {
        return courseRepository.findByTeacher(teacher);
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}
