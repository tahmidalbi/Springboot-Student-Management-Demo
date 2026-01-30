package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"teachers", "students", "courses"})
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Teacher> teachers;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Student> students;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Course> courses;

    public Department(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Set<Teacher> getTeachers() {
        if (teachers == null) {
            teachers = new HashSet<>();
        }
        return teachers;
    }

    public Set<Student> getStudents() {
        if (students == null) {
            students = new HashSet<>();
        }
        return students;
    }

    public Set<Course> getCourses() {
        if (courses == null) {
            courses = new HashSet<>();
        }
        return courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
