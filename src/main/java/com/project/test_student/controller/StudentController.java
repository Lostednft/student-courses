package com.project.test_student.controller;

import com.project.test_student.domain.Student;
import com.project.test_student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController{

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public ResponseEntity saveStudent(@RequestBody Student student){

        return ResponseEntity.ok(studentRepository.save(student));
    }

    @GetMapping
    public ResponseEntity findAllStudent(){

        return ResponseEntity.ok(studentRepository.findAll());
    }
}

