package com.project.test_student.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "discipline_tb")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Discipline{

    @Id
    private Integer id;
    private String discipline;


    public enum DisciplineEnum {

        MATHEMATICS(0, "mathematics"),
        SCIENCE(1, "science"),
        HISTORY(2, "history"),
        BIOLOGY(3, "biology");

        private Integer id;
        private String discipline;

        DisciplineEnum(Integer id, String discipline) {
            this.id = id;
            this.discipline = discipline;
        }

        public Discipline toDiscipline() {
            return new Discipline(id, discipline.toUpperCase());
        }

    }
}
