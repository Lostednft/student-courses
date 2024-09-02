package StudentServiceTest;

import com.project.test_student.domain.Course;
import com.project.test_student.domain.Student;
import com.project.test_student.service.StudentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setup(){

        student = new Student();
        student.setName("felipe");
        student.setEmail("felip.surf23@gmail.br");
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
    void givenStudentEmail_whenValidEmail_thenReturnBooleanValue(){

        //GIVEN - not necessary

        //WHEN
        boolean emailValidate = studentService.emailIsValid(student.getEmail());

        //THEN
        Assertions.assertThat(emailValidate).isTrue();
    }
}
