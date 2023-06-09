package com.tkpm.studentsmanagement.entity;

import java.util.List;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "class")
@EntityListeners(AuditingEntityListener.class) // listener auditing
public class ClassEntity extends AbstractEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "numberOfPupils")
    private Integer numberOfPupils = 0;

    @Column(name = "year")
    private String year;

    // relationship
    @OneToMany(mappedBy = "classs", fetch = FetchType.LAZY)
    private List<ClassStudentEntity> classStudent;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfPupils() {
        return this.numberOfPupils;
    }

    public void setNumberOfPupils(Integer number_of_pupils) {
        this.numberOfPupils = number_of_pupils;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<ClassStudentEntity> getClassStudent() {
        return this.classStudent;
    }

    public void setClassStudent(List<ClassStudentEntity> class_student) {
        this.classStudent = class_student;
    }
}
