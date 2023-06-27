package com.tkpm.studentsmanagement.dto;

/**
 * @author : daitt
 * @create : 12/6/2023
 **/

public class SubjectDTO extends AbstractDTO {
    private String name;
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
