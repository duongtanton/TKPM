package com.tkpm.studentsmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author : daitt
 * @create : 12/6/2023
 **/
@Entity(name = "subject")
@EntityListeners(AuditingEntityListener.class)
public class SubjectEntity extends AbstractEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "requiredScore")
    private Double requiredScore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRequiredScore() {
        return requiredScore;
    }

    public void setRequiredScore(Double requiredScore) {
        this.requiredScore = requiredScore;
    }
}
