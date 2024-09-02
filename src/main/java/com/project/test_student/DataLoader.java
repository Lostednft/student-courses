package com.project.test_student;

import com.project.test_student.domain.Course;
import com.project.test_student.domain.Student;
import com.project.test_student.domain.StudentDto;
import com.project.test_student.repository.CourseRepository;
import com.project.test_student.repository.StudentRepository;
import com.project.test_student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private StudentService service;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void run(String... args) throws Exception {

        studentRepository.deleteAll();
        courseRepository.deleteAll();

        courseRepository.saveAll(List.of(
                Course.CourseEnum.HISTORY.toCourse(),
                Course.CourseEnum.SCIENCE.toCourse(),
                Course.CourseEnum.MATHEMATICS.toCourse(),
                Course.CourseEnum.BIOLOGY.toCourse()));

        Student student0 = new Student(new StudentDto("felipe",List.of(Course.CourseEnum.HISTORY, Course.CourseEnum.BIOLOGY), "felipe@gmail.com", 59L));
        Student student1 = new Student(new StudentDto("Maria", List.of(Course.CourseEnum.SCIENCE, Course.CourseEnum.HISTORY), "maria@gmail.com", 21L));
        Student student2 = new Student(new StudentDto("Julia", List.of(Course.CourseEnum.MATHEMATICS, Course.CourseEnum.BIOLOGY), "julia@gmail.com", 40L));

        studentRepository.saveAll(List.of(student0, student1, student2));
    }
}
