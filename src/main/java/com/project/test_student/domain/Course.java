package com.project.test_student.domain;

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
    private Long id;
    private String courseName;

    @Getter
    public enum CourseEnum {

        MATHEMATICS(0L, "mathematics"),
        SCIENCE(1L, "science"),
        HISTORY(2L, "history"),
        BIOLOGY(3L, "biology");

        private Long id;
        private String courseName;

        CourseEnum(Long id, String courseName) {
            this.id = id;
            this.courseName = courseName;
        }

        public Course toCourse() {
            return new Course(id, courseName);
        }
    }
}
