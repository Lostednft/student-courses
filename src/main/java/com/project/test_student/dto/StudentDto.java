package com.project.test_student.dto;

import com.project.test_student.domain.Course;

import java.util.Set;

public record StudentDto(String name,
                         String email,
                         Long hoursAttended,
                         Set<Course.CourseEnum> courses) {
}
