package com.project.test_student.dto;

import com.project.test_student.domain.Course;

import java.util.List;
import java.util.Set;

public record StudentUpdateDto(String id,
                         String name,
                         String email,
                         Long hoursAttended,
                         Set<Course.CourseEnum> courses,
                         List<Double> testScores) {
}