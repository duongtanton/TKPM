package com.tkpm.studentsmanagement.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity(name = "class")
@EntityListeners(AuditingEntityListener.class) // listener auditing
public class ClassEntity extends AbstractEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "numberOfPupils")
    private Integer numberOfPupils;

    // relationship
    @ManyToMany(mappedBy = "classes",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<StudentEntity> students;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "test_class", joinColumns = @JoinColumn(name = "classId"), inverseJoinColumns = @JoinColumn(name = "testId"))
    @JsonManagedReference
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
