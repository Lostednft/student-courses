package com.project.test_student.StudentRepositoryTest;

import com.project.test_student.AbstractionBaseTest;
import com.project.test_student.domain.Course;
import com.project.test_student.domain.Student;
import com.project.test_student.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTests extends AbstractionBaseTest {

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
    void givenObjectStudent_whenSaveObjectAndFindById_thenReturnStudent(){

        //GIVEN
        studentRepository.save(student);

        //WHEN
        Student byId = studentRepository.findById(student.getId()).get();

        //THEN
        Assertions.assertThat(byId).isNotNull();
        Assertions.assertThat(byId).isEqualTo(student);
        Assertions.assertThat(byId.getEmail()).isEqualTo(student.getEmail());
    }

    @Test
    void givenStudentsObjects_whenFindAll_thenReturnStudentList(){

        //GIVEN
        Student studentNew = student;
        studentNew.setId("k12391312k129312jk31m0");
        studentNew.setName("Julia");
        studentNew.setEmail("julia.sz@hotmail.com");
        studentNew.setTestScoreAverage(5.5);
        studentNew.setHoursAttended(1105L);
        studentNew.setHoursRequired(1300L);
        studentNew.setCourses(Set.of(
                Course.CourseEnum.BIOLOGY.toCourse(studentNew),
                Course.CourseEnum.MATHEMATICS.toCourse(studentNew)));
        studentRepository.saveAll(List.of(student, studentNew));

        //WHEN
        List<Student> students = studentRepository.findAll();

        //THEN
        Assertions.assertThat(students).isNotNull();
        Assertions.assertThat(students.size()).isEqualTo(2);
        Assertions.assertThat(students.get(0).getEmail()).isEqualTo(student.getEmail());
        Assertions.assertThat(students.get(1).getTestScoreAverage()).isEqualTo(studentNew.getTestScoreAverage());

    }

    @Test
    void givenStudentSaved_whenDeleteObject_thenReturnNothing(){

        //GIVEN
        studentRepository.save(student);

        //WHEN
        studentRepository.delete(student);
        Student studentById = studentRepository.findById(student.getId()).orElse(null);

        //THEN
        Assertions.assertThat(studentById).isNull();
    }


}
