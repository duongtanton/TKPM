package com.tkpm.studentsmanagement.entity;

import java.sql.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity(name = "student")
@EntityListeners(AuditingEntityListener.class) // listener auditing
public class StudentEntity extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "birthDate")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "sex") // male 1: female: 0
    private Boolean sex;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    // relationship
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_class", joinColumns = @JoinColumn(name = "studentId"), inverseJoinColumns = @JoinColumn(name = "classId"))
    @JsonManagedReference
    private List<ClassEntity> classes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_test", joinColumns = @JoinColumn(name = "studentId"), inverseJoinColumns = @JoinColumn(name = "testId"))
    @JsonManagedReference
    private List<TestEntity> tests;
    // end relationship

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean isSex() {
        return this.sex;
    }

    public Boolean getSex() {
        return this.sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
