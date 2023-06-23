package com.tkpm.studentsmanagement.dto;


public class ReportDTO extends AbstractDTO {
    private Long classId;
    private Long subjectId;
    private String type;
    private Integer year;
    private Integer semester;
    private Double averageScore;
    private Integer numberOfPupils;
    private Integer passQuantity;
    private Double passPercent;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getNumberOfPupils() {
        return numberOfPupils;
    }

    public void setNumberOfPupils(Integer numberOfPupils) {
        this.numberOfPupils = numberOfPupils;
    }

    public Integer getPassQuantity() {
        return passQuantity;
    }

    public void setPassQuantity(Integer passQuantity) {
        this.passQuantity = passQuantity;
    }

    public Double getPassPercent() {
        return passPercent;
    }

    public void setPassPercent(Double passPercent) {
        this.passPercent = passPercent;
    }
}
