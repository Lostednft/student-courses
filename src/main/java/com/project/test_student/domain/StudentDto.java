package com.project.test_student.domain;

import java.util.List;

public record StudentDto(String name,
                         List<Course.CourseEnum> courses,
                         String email,
                         Long hoursAttended) {
}
