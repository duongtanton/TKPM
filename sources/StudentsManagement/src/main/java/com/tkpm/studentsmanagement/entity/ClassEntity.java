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
    private Integer numberOfPupils;

    // relationship
    @OneToMany(mappedBy = "classEntity",fetch = FetchType.LAZY)
    private List<ClassStudentEntity> class_student;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "test_class", joinColumns = @JoinColumn(name = "classId"), inverseJoinColumns = @JoinColumn(name = "testId"))
    private List<TestEntity> tests;
    // end relationship

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfPupils() {
        return this.numberOfPupils;
    }

    public void setNumberOfPupils(Integer numberOfPupils) {
        this.numberOfPupils = numberOfPupils;
    }

}
