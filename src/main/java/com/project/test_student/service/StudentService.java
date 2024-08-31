package com.project.test_student.service;

import com.project.test_student.domain.Student;
import com.project.test_student.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student saveStudent(Student student){

        return studentRepository.save(student);
    }
}
