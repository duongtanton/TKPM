package com.tkpm.studentsmanagement.entity;

import java.util.List;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "class")
@EntityListeners(AuditingEntityListener.class) // listener auditing
public class ClassEntity extends AbstractEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "number_of_pupils")
    private Integer number_of_pupils = 0;

    @Column(name = "school_year")
    private String school_year;

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
        return this.number_of_pupils;
    }

    public void setNumberOfPupils(Integer number_of_pupils) {
        this.number_of_pupils = number_of_pupils;
    }

    public String getSchool_year() {
        return school_year;
    }

    public void setSchool_year(String school_year) {
        this.school_year = school_year;
    }
}
