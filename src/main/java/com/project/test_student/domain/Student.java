package com.project.test_student.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "student_tb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    @JoinTable(name = "course_student_tb",
    joinColumns = @JoinColumn(name = "student_id"),
    inverseJoinColumns = @JoinColumn(name = "class_tb"))
    @ManyToMany
    private List<Course> courses = new ArrayList<>();
    private String email;
    private Long hoursAttended;

    public Student(StudentDto studentDto){
        this.name = studentDto.name();
        this.courses = studentDto.courses().stream()
                .map(t -> Course.CourseEnum.valueOf(t.getCourseName().toUpperCase()).toCourse())
                .collect(Collectors.toList());
        this.email = studentDto.email();
        this.hoursAttended = studentDto.hoursAttended();
    }
}