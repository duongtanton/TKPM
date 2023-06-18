package com.tkpm.studentsmanagement.entity;

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "class_student")
@EntityListeners(AuditingEntityListener.class) // listener auditing
public class ClassStudentEntity extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "class_id")
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
    private ClassEntity classEntity;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity studentEntity;

    @Column
    private Boolean status;

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public StudentEntity getStudentEntity() {
        return studentEntity;
    }

    public void setStudentEntity(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}