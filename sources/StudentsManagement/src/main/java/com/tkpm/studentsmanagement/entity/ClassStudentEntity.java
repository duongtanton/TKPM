package com.tkpm.studentsmanagement.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "class_student")
@EntityListeners(AuditingEntityListener.class) // listener auditing
public class ClassStudentEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classId")
    private ClassEntity classs;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentId")
    private StudentEntity student;

    @Column
    private Boolean status;

    public ClassEntity getClasss() {
        return classs;
    }

    public void setClasss(ClassEntity classs) {
        this.classs = classs;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}