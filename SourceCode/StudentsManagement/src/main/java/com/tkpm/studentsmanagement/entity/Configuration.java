package com.tkpm.studentsmanagement.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;

@Entity(name = "configuration")
@EntityListeners(AuditingEntityListener.class) // listener auditing
public class Configuration extends AbstractEntity {

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "value") //type 
    private String value;

    @Column(name = "description")
    private String description;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}