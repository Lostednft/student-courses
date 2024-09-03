package com.project.test_student.controller;

import com.project.test_student.dto.StudentDto;
import com.project.test_student.dto.StudentUpdateDto;
import com.project.test_student.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController{


    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity saveStudent(@RequestBody StudentDto studentDto){

        studentService.saveStudent(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Created successfully!");
    }

    @GetMapping
    public ResponseEntity findAllStudent(){

        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @GetMapping("/{id}")
    public ResponseEntity findStudentsById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(studentService.getStudentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable String id){

        studentService.studentDeleteById(id);
        return ResponseEntity.ok("Deleted with Success!!");
    }

    @PutMapping
    public ResponseEntity updateStudentById(@RequestBody StudentUpdateDto student){

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(studentService.updateStudentById(student));
    }
}

