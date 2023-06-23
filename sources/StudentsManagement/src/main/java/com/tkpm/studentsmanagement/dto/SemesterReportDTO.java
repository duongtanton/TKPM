package com.tkpm.studentsmanagement.dto;

public class SemesterReportDTO extends AbstractDTO {
    private Long classId;
    private String className;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
