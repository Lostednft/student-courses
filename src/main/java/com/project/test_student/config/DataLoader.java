package com.project.test_student.config;

import com.project.test_student.domain.Course;
import com.project.test_student.domain.Student;
import com.project.test_student.repository.StudentRepository;
import com.project.test_student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class DataLoader implements CommandLineRunner {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;


    @Override
    public void run(String... args) throws Exception {

        studentRepository.deleteAll();


        Student student = new Student();
        student.setName("felipe");
        student.setEmail("felipe@gmail.com");
        student.setHoursAttended(1200L);
        student.setTestScoreAverage(8.96);

        Set<Course> courses = Set.of(Course.CourseEnum.HISTORY.toCourse(student),
                Course.CourseEnum.MATHEMATICS.toCourse(student));

        Long hoursRequiredTotal = courses.stream()
                .mapToLong(Course::getHoursRequired)
                .reduce(0L, Long::sum);

        student.setHoursRequired(hoursRequiredTotal);
        student.setCourses(courses);

        studentRepository.save(student);
    }
}
