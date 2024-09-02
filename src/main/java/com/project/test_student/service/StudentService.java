package com.project.test_student.service;

import com.project.test_student.domain.Course;
import com.project.test_student.domain.Student;
import com.project.test_student.dto.StudentDto;
import com.project.test_student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student saveStudent(StudentDto studentDto){

        Student student = new Student();
        student.setName(studentDto.name());
        student.setEmail(studentDto.email());
        student.setHoursAttended(studentDto.hoursAttended());

        Set<Course> listCourses = studentDto.courses().stream().map(n -> Course.CourseEnum
                        .valueOf(n.getCourseName().toUpperCase())
                        .toCourse(student))
                .collect(Collectors.toSet());

        Long hoursRequiredTotal = studentDto.courses().stream()
                .mapToLong(Course.CourseEnum::getHoursRequired)
                .reduce(0L, Long::sum);

        student.setHoursRequired(hoursRequiredTotal);
        student.setCourses(listCourses);

        return studentRepository.save(student);
    }
}
