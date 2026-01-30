package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"courses", "department"})
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    @Getter(AccessLevel.NONE)
    private Set<Course> courses;

    private String role = "TEACHER";

    public Set<Course> getCourses() {
        if (courses == null) {
            courses = new HashSet<>();
        }
        return courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return id != null && id.equals(teacher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
