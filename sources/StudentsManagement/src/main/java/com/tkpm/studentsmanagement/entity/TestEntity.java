package com.tkpm.studentsmanagement.entity;

import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;

@Entity(name = "test")
@EntityListeners(AuditingEntityListener.class) // listener auditing
public class TestEntity extends AbstractEntity {

    @Column(name = "type")
    private Integer type; // using TestType

    @Column(name = "score")
    private Float score;

    // relationship
    @ManyToMany(mappedBy = "tests", fetch = FetchType.LAZY)
    private List<StudentEntity> students;

    @ManyToMany(mappedBy = "tests", fetch = FetchType.LAZY)
    private List<ClassEntity> classes;
    // end relationship

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer Integer) {
        this.type = Integer;
    }

    public Float getScore() {
        return this.score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
