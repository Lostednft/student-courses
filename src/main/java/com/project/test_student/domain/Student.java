package com.project.test_student.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
    private String email;
    private Long hoursAttended;
    private Long hoursRequired;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Course> courses = new HashSet<>();



}