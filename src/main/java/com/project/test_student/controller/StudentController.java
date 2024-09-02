package com.project.test_student.controller;

import com.project.test_student.domain.Course;
import com.project.test_student.domain.Student;
import com.project.test_student.domain.StudentDto;
import com.project.test_student.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController{

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public ResponseEntity saveStudent(@RequestBody StudentDto studentDto){

        Student student = new Student(studentDto);
        return ResponseEntity.ok(studentRepository.save(student));
    }

    @GetMapping
    public ResponseEntity findAllStudent(){

        return ResponseEntity.ok(studentRepository.findAll());
    }
}

