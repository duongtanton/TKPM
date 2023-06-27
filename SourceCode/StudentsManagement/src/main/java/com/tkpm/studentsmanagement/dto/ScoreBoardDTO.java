package com.tkpm.studentsmanagement.dto;

/**
 * @author : daitt
 * @create : 5/6/2023
 **/

public class ScoreBoardDTO extends AbstractDTO {
    private StudentDTO student;
    private ClassDTO classs;
    private SubjectDTO subject;
    private Double exam15Min;
    private Double exam45Min;
    private Double examMiddle;
    private Double examFinal;
    private Double averageScore;
    private Integer semester;
    private Integer year;
    private Boolean isCompleted;

    public StudentDTO getStudent() {
        return this.student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public ClassDTO getClasss() {
        return this.classs;
    }

    public void setClasss(ClassDTO classs) {
        this.classs = classs;
    }

    public SubjectDTO getSubject() {
        return this.subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }

    public Double getExam15Min() {
        return this.exam15Min;
    }

    public void setExam15Min(Double exam15Min) {
        this.exam15Min = exam15Min;
    }

    public Double getExam45Min() {
        return this.exam45Min;
    }

    public void setExam45Min(Double exam45Min) {
        this.exam45Min = exam45Min;
    }

    public Double getExamMiddle() {
        return this.examMiddle;
    }

    public void setExamMiddle(Double examMiddle) {
        this.examMiddle = examMiddle;
    }

    public Double getExamFinal() {
        return this.examFinal;
    }

    public void setExamFinal(Double examFinal) {
        this.examFinal = examFinal;
    }

    public Double getAverageScore() {
        return this.averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getSemester() {
        return this.semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean isIsCompleted() {
        return this.isCompleted;
    }

    public Boolean getIsCompleted() {
        return this.isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
