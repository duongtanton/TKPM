package com.tkpm.studentsmanagement.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;

@Entity(name = "otp")
@EntityListeners(AuditingEntityListener.class) // listener auditing
public class OtpEntity extends AbstractEntity {
    
    @Column(name = "content", columnDefinition = "text")
    private String content;


    @Column(name = "used", columnDefinition = "boolean DEFAULT 0")
    private Boolean used;

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean isUsed() {
        return this.used;
    }

    public Boolean getUsed() {
        return this.used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }
}
