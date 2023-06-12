package com.tkpm.studentsmanagement.dto;

/**
 * @author : daitt
 * @create : 12/6/2023
 **/

public class SubjectDTO extends AbstractDTO {
    private String name;
    private Long teacherID;
    private Double requiredScore;

    //Teacher list
    //score board list
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Long teacherID) {
        this.teacherID = teacherID;
    }

    public Double getRequiredScore() {
        return requiredScore;
    }

    public void setRequiredScore(Double requiredScore) {
        this.requiredScore = requiredScore;
    }

    @Override
    public String toString() {
        return "SubjectDTO{" +
                "name='" + name + '\'' +
                ", teacherID=" + teacherID +
                ", requiredScore=" + requiredScore +
                '}';
    }
}
