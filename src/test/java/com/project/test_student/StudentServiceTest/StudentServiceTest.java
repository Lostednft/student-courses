package com.project.test_student.StudentServiceTest;

import com.project.test_student.domain.Course;
import com.project.test_student.domain.Student;
import com.project.test_student.dto.StudentDto;
import com.project.test_student.dto.StudentUpdateDto;
import com.project.test_student.repository.StudentRepository;
import com.project.test_student.service.StudentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;


public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;
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

        //WHEN
        List<Student> allStudent = studentService.getAllStudent();

        //THEN
        Assertions.assertThat(allStudent).isEqualTo(List.of(studentTemp, student));
        Assertions.assertThat(allStudent.get(0).getName()).isEqualTo(studentTemp.getName());
        Assertions.assertThat(allStudent.get(1).getHoursAttended()).isEqualTo(student.getHoursAttended());
    }

    @Test
    void givenStudentObject_whenGetStudentById_thenReturnStude() {

        //GIVEN
        studentRepository.save(student);
        BDDMockito.given(studentRepository.findById(student.getId())).willReturn(Optional.of(student));

        //WHEN
        Student studentById = studentService.getStudentById(student.getId());
        //THEN
        Assertions.assertThat(studentById).isEqualTo(student);
        Assertions.assertThat(studentById.getId()).isEqualTo(student.getId());
        Assertions.assertThat(studentById).isNotNull();
    }


    @Test
    void givenStudentSaved_whenDeleteStudentById_thenReturnStudentListEmpty(){

        //GIVEN
        BDDMockito.given(studentRepository.findById(student.getId())).willReturn(Optional.of(student));

        studentRepository.save(student);
        Student studentSaved = studentRepository.findById(student.getId()).get();

        //WHEN
        studentService.studentDeleteById(student.getId());
        List<Student> students = studentRepository.findAll();
        //THEN

        Assertions.assertThat(students.size()).isEqualTo(0);
        Assertions.assertThat(studentSaved.getId()).isEqualTo(student.getId());
    }

    @Test
    void given_when_then(){

        //GIVEN
        BDDMockito.given(studentRepository.save(any(Student.class))).willReturn(student);
        BDDMockito.given(studentRepository.findById(student.getId())).willReturn(Optional.of(student));
        studentRepository.save(student);

        StudentUpdateDto studentUpdate = new StudentUpdateDto(
                student.getId(),
                "julia",
                "julia.sz@gmail.com",
                1994L,
                studentDto.courses(),
                studentDto.testScores());

        //WHEN
        studentService.updateStudentById(studentUpdate);

        Student studentUpdated = studentRepository.findById(studentUpdate.id()).get();
        //THEN

        Assertions.assertThat(studentUpdated.getEmail()).isEqualTo("julia.sz@gmail.com");
        Assertions.assertThat(studentUpdated.getHoursAttended()).isEqualTo(1994L);
        Assertions.assertThat(studentUpdated.getCourses()).isEqualTo(student.getCourses());
    }
}
