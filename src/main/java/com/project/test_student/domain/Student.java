package com.project.test_student.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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


    public Student(String name, List<Course> courses, String email, Long hoursAttended){

        this.name = name;
        this.courses.addAll(courses);
        this.email = email;
        this.hoursAttended = hoursAttended;

    }

    public Student(StudentDto studentDto){

        this.name = studentDto.name();
        this.courses = studentDto.courses().stream()
                .map(t -> Course.CourseEnum.valueOf(t.getCourseName().toUpperCase()).toCourse())
                .collect(Collectors.toList());
        this.email = studentDto.email();
        this.hoursAttended = studentDto.hoursAttended();
    }
}