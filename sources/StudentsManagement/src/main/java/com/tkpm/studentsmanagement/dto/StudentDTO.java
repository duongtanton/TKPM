package com.tkpm.studentsmanagement.dto;

import java.sql.Timestamp;

public class StudentDTO extends AbstractDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudentDTO(String name, Timestamp createdDate, Timestamp updatedDate, Long createdBy, Long updatedBy) {
        this.name = name;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
    public StudentDTO(){

    }
}
