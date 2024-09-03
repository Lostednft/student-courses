package com.project.test_student.service;

import com.project.test_student.domain.Course;
import com.project.test_student.domain.Student;
import com.project.test_student.dto.StudentDto;
import com.project.test_student.dto.StudentUpdateDto;
import com.project.test_student.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;
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
        student.setEmail(emailIsValid(studentDto.email()));
        student.setHoursAttended(studentDto.hoursAttended());
        student.setTestScoreAverage(averageTestFunction(studentDto.testScores()));
        student.setHoursRequired(hoursRequiredCalculator(studentDto.courses()));
        student.setCourses(selectCourses(studentDto.courses(), student));

        return studentRepository.save(student);
    }

    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }

    public Student getStudentById(String studentId){
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("No students found with this ID."));
    }


    public void studentDeleteById(String studentId){

       Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new NoSuchElementException("No students found with this ID."));

        studentRepository.delete(student);
    }


    @Transactional
    public Student updateStudentById(StudentUpdateDto student) {

       Student studentById = studentRepository.findById(student.id())
                .orElseThrow(() -> new NoSuchElementException("No students found with this ID."));

        studentById.setName(student.name());
        studentById.setCourses(selectCourses(student.courses(),studentById));
        studentById.setTestScoreAverage(averageTestFunction(student.testScores()));
        studentById.setEmail(emailIsValid(student.email()));
        studentById.setHoursAttended(student.hoursAttended());
        studentById.setHoursRequired(hoursRequiredCalculator(student.courses()));

        studentRepository.save(studentById);
       return studentById;
    }

    public Set<Course> selectCourses(Set<Course.CourseEnum> courses, Student student){
               return courses.stream()
                       .map(n-> Course.CourseEnum.valueOf(n.getCourseName().toUpperCase())
                        .toCourse(student))
                .collect(Collectors.toSet());
    }

    public long hoursRequiredCalculator(Set<Course.CourseEnum> courses){
        return  courses.stream()
                .mapToLong(Course.CourseEnum::getHoursRequired)
                .reduce(0L, Long::sum);

    }

    public String emailIsValid(String email){

        String regex = "[\\S]+@[a-zA-Z]+[.a-zA-Z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches())
            throw new InvalidParameterException("Email invalid.");

        return email;
    }

    public Double averageTestFunction(List<Double> testScores){

        double averageTests = testScores.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        return new BigDecimal(averageTests)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

    }


}
