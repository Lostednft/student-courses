package com.project.test_student.service;

import com.project.test_student.domain.Course;
import com.project.test_student.domain.Student;
import com.project.test_student.dto.StudentDto;
import com.project.test_student.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public Student saveStudent(StudentDto studentDto){

        Student student = new Student();
        student.setName(studentDto.name());
        student.setEmail(studentDto.email());
        student.setHoursAttended(studentDto.hoursAttended());
        student.setTestScoreAverage(averageTestFunction(studentDto));

        Set<Course> listCourses = studentDto.courses().stream()
                .map(n -> Course.CourseEnum.valueOf(n.getCourseName().toUpperCase())
                        .toCourse(student))
                .collect(Collectors.toSet());

        Long hoursRequiredTotal = studentDto.courses().stream()
                .mapToLong(Course.CourseEnum::getHoursRequired)
                .reduce(0L, Long::sum);

        student.setHoursRequired(hoursRequiredTotal);
        student.setCourses(listCourses);

        return studentRepository.save(student);
    }


     public boolean emailIsValid(String email){

        String regex = "[\\S]+@[a-zA-Z]+[.a-zA-Z]+";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }


    private Double averageTestFunction(StudentDto testScores){

        Double averageTests = testScores.testScores()
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        return new BigDecimal(averageTests)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

    }
}
