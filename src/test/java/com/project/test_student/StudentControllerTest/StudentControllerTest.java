package com.project.test_student.StudentControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.test_student.AbstractionBaseTest;
import com.project.test_student.domain.Course;
import com.project.test_student.domain.Student;
import com.project.test_student.dto.StudentDto;
import com.project.test_student.dto.StudentUpdateDto;
import com.project.test_student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest extends AbstractionBaseTest{

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ObjectMapper objectMapper;

    StudentDto studentDto;

    Student student;

    @BeforeEach
    void setup(){

        studentRepository.deleteAll();

        studentDto = new StudentDto(
                "felipe",
                "felipe.xd@gmail.com",
                1200L,
                Set.of(Course.CourseEnum.MATHEMATICS,
                        Course.CourseEnum.BIOLOGY),
                List.of(5.3, 1.4, 6.5, 7.6));

        student = new Student();
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
    void givenStudentDtoObject_whenPostSaveStudent_thenReturnStatusCreated() throws Exception {

        //GIVEN - Created by setup.

        //WHEN
        ResultActions response = mockMvc.perform(post("/student")
                .with(user("admin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDto)));

        response.andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    void givenStudentListSaved_whenGetAllStudents_thenReturnStudents() throws Exception{

        //GIVEN
        Student studentNew = new Student();

        studentNew.setId("19319FASDA");
        student.setName("Maria");
        student.setEmail("maria.souza@gmail.com");
        student.setHoursAttended(995L);
        student.setTestScoreAverage(5.5);

        studentRepository.saveAll(List.of(student, studentNew));

        //WHEN
        ResultActions response = mockMvc.perform(get("/student")
                .with(user("admin")));

        //THEN

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[1].testScoreAverage").value(studentNew.getTestScoreAverage()));

    }

    @Test
    void givenStudentSaved_whenFindStudentById_thenReturnStudent() throws Exception{

        //GIVEN
        Student studentSaved = studentRepository.save(student);

        //WHEN
        ResultActions response = mockMvc.perform(get("/student/{id}", studentSaved.getId())
                .with(user("admin")));
        //THEN

        response.andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.hoursAttended").value(student.getHoursAttended()));
    }

    @Test
    void givenStudentSaved_whenDeleteStudent_thenNothing() throws Exception{

        //GIVEN
        Student studentSaved = studentRepository.save(student);

        //WHEN
        ResultActions response = mockMvc.perform(delete("/student/{id}", studentSaved.getId())
                .with(user("admin")));

        //THEN
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").isEmpty());


    }


    @Test
    void givenStudentSaved_whenUpdateStudent_thenReturnStudentUpdated() throws Exception{

        //GIVEN
        studentRepository.save(student);

        StudentUpdateDto studentToUpdate = new StudentUpdateDto(
                student.getId(),
                "Lucas",
                "lucas.vlr@gmail.com",
                2230L,
                studentDto.courses(),
                studentDto.testScores());

        //WHEN
        ResultActions response = mockMvc.perform(put("/student")
                .with(user("admin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentToUpdate)));

        //THEN
        response.andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value(studentToUpdate.name()))
                .andExpect(jsonPath("$.email").value(studentToUpdate.email()))
                .andExpect(jsonPath("$.hoursAttended").value(studentToUpdate.hoursAttended()))
                .andExpect(jsonPath("$.hoursRequired").value(student.getHoursRequired()));
    }
}
