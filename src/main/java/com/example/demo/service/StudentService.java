package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<Student> findByRollNo(String rollNo) {
        return studentRepository.findByRollNo(rollNo);
    }

    public boolean existsByRollNo(String rollNo) {
        return studentRepository.existsByRollNo(rollNo);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}
