package com.project.test_student.repository;

import com.project.test_student.domain.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplineRepository extends JpaRepository<Discipline, Integer> {

}
