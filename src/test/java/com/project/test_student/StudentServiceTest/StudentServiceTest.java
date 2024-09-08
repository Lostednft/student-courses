package com.project.test_student.StudentServiceTest;

import com.project.test_student.domain.Course;
import com.project.test_student.domain.Student;
import com.project.test_student.dto.StudentDto;
import com.project.test_student.repository.StudentRepository;
import com.project.test_student.service.StudentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;


public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;
    private Student student;
    private StudentDto studentDto;

    @BeforeEach
    void setup(){
        studentRepository = Mockito.mock(StudentRepository.class);
        studentService = new StudentService(studentRepository);

        studentDto = new StudentDto(
                "felipe",
                "felipe.xd@gmail.com",
                1200L,
                Set.of(Course.CourseEnum.MATHEMATICS,
                        Course.CourseEnum.BIOLOGY),
                List.of(5.3, 1.4, 6.5, 7.6));

        student = new Student();
        student.setId("12931jo1jk239129j9");
        student.setName(studentDto.name());
        student.setEmail(studentDto.email());
        student.setHoursAttended(studentDto.hoursAttended());
        student.setTestScoreAverage(studentDto.testScores().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0));

        Set<Course> courses = studentDto.courses().stream()
                .map(n-> Course.CourseEnum.valueOf(
                        n.getCourseName()
                        .toUpperCase())
                        .toCourse(student))
                .collect(Collectors.toSet());

        Long hoursRequiredTotal = courses.stream()
                .mapToLong(Course::getHoursRequired)
                .reduce(0L, Long::sum);

        student.setHoursRequired(hoursRequiredTotal);
        student.setCourses(courses);
    }

    @Test
    void givenStudentEmail_whenValidEmail_thenReturnBooleanValue(){

        //GIVEN - not necessary

        //WHEN
        String emailValidate = studentService.emailIsValid(student.getEmail());

        //THEN
        Assertions.assertThat(emailValidate).isEqualTo(student.getEmail());
    }


    @Test
    void givenStudentDtoObject_whenSaveStudentDto_thenReturnStudent(){

        //GIVEN
        BDDMockito.given(studentRepository.save(any(Student.class))).willReturn(student);
        BDDMockito.given(studentService.saveStudent(studentDto)).willReturn(student);

        //WHEN
        Student std = studentService.saveStudent(studentDto);

        //THEN
        Assertions.assertThat(std).isEqualTo(student);
        Assertions.assertThat(std.getName()).isEqualTo(student.getName());
        Assertions.assertThat(std.getCourses()).isEqualTo(student.getCourses());
    }

    @Test
    void givenStudentList_whenFindAll_thenReturnStudentList(){

        //GIVEN
        Student studentTemp = new Student();
        studentTemp.setName("marcos");
        studentTemp.setId("1923129");
        studentTemp.setHoursAttended(1925L);

        studentRepository.saveAll(List.of(studentTemp, student));

        BDDMockito.given(studentRepository.findAll()).willReturn(List.of(studentTemp, student));
        BDDMockito.given(studentService.getAllStudent()).willReturn(List.of(studentTemp, student));

        //WHEN
        List<Student> allStudent = studentService.getAllStudent();

        //THEN
        Assertions.assertThat(allStudent).isEqualTo(List.of(studentTemp, student));
        Assertions.assertThat(allStudent.get(0).getName()).isEqualTo(studentTemp.getName());
        Assertions.assertThat(allStudent.get(1).getHoursAttended()).isEqualTo(student.getHoursAttended());
    }
}
