package com.project.test_student.dto;

public record CourseDto(Long id,
                        String courseName,
                        Long hoursAttended,
                        Long hoursRequired) {
}
