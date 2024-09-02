package com.project.test_student.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String courseName;
    private Long hoursRequired;
    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Student student;

    public Course(String courseName, Long hoursRequired, Student student) {
        this.courseName = courseName;
        this.hoursRequired = hoursRequired;
        this.student = student;
    }

    @Getter
    public enum CourseEnum {

        MATHEMATICS("mathematics", 350L),
        SCIENCE("science", 300L),
        HISTORY(  "history", 380L),
        BIOLOGY( "biology", 290L);

        private String courseName;
        private Long hoursRequired;

        CourseEnum(String courseName, Long hoursRequiredParam) {
            this.courseName = courseName;
            this.hoursRequired = hoursRequiredParam;
        }

        public Course toCourse( Student student) {
            return new Course(courseName, hoursRequired,  student);
        }
    }
}
