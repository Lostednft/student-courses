package com.project.test_student.controller;

import com.project.test_student.domain.Student;
import com.project.test_student.dto.StudentDto;
import com.project.test_student.dto.StudentUpdateDto;
import com.project.test_student.repository.StudentRepository;
import com.project.test_student.service.StudentService;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/student")
public class StudentController{

    private final StudentRepository studentRepository;

    private final StudentService studentService;

    public StudentController(StudentRepository studentRepository,
                             StudentService studentService){
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity saveStudent(@RequestBody StudentDto studentDto){

        Student student = studentService.saveStudent(studentDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentRepository.save(student));
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
        Optional<Student> studentById = studentRepository.findById(id);

        studentRepository.delete(studentById.get());
        return ResponseEntity.ok("Deleted with Success!!");
    }

    @PutMapping
    public ResponseEntity updateStudentById(@RequestBody StudentUpdateDto student){

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(studentService.updateStudentById(student));
    }
}

