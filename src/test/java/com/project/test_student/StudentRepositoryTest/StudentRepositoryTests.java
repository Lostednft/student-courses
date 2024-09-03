package com.project.test_student.StudentRepositoryTest;

import com.project.test_student.domain.Course;
import com.project.test_student.domain.Student;
import com.project.test_student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

@DataJpaTest
public class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    void setup(){

        student = new Student();
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
    }

    @Test
    void given_when_then(){

        studentRepository.save()
    }
}
