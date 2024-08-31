package com.project.test_student.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(fetch = FetchType.EAGER)
    private List<Discipline> discipline = new ArrayList<>();
    private String email;
    private long hoursAttended;


    public Student(String name, List<Discipline> discipline, String email, long hoursAttended){

        this.name = name;
        this.discipline.addAll(discipline);
        this.email = email;
        this.hoursAttended = hoursAttended;
    }
}