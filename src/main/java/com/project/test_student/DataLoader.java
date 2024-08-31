package com.project.test_student;

import com.project.test_student.domain.Discipline;
import com.project.test_student.domain.Student;
import com.project.test_student.repository.DisciplineRepository;
import com.project.test_student.repository.StudentRepository;
import com.project.test_student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private StudentService service;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Override
    public void run(String... args) throws Exception {

        studentRepository.deleteAll();

        disciplineRepository.saveAll(List.of(
                Discipline.DisciplineEnum.HISTORY.toDiscipline(),
                Discipline.DisciplineEnum.SCIENCE.toDiscipline(),
                Discipline.DisciplineEnum.MATHEMATICS.toDiscipline(),
                Discipline.DisciplineEnum.BIOLOGY.toDiscipline()));

        Student student0 = new Student("felipe", List.of(Discipline.DisciplineEnum.HISTORY.toDiscipline()), "felipe@gmail.com", 59L);
        Student student1 = new Student("Maria", List.of(Discipline.DisciplineEnum.SCIENCE.toDiscipline()), "maria@gmail.com", 21L);
        Student student2 = new Student("Julia", List.of(Discipline.DisciplineEnum.MATHEMATICS.toDiscipline()), "julia@gmail.com", 40L);
        studentRepository.saveAll(List.of(student0, student1, student2));
    }
}
