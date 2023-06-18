package com.tkpm.studentsmanagement.dto;

/**
 * @author : daitt
 * @create : 5/6/2023
 **/

public class ScoreBoardDTO extends AbstractDTO{
    private Long studentId;
    private Long classId;
    private Long subjectId;
    private Double exam15Min;
    private Double exam45Min;
    private Double examMiddle;
    private Double examFinal;
    private Double averageScore;
    private int semester;
    private int year;
    private boolean isCompleted;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

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

    public Double getExam15Min() {
        return exam15Min;
    }

    public void setExam15Min(Double exam15Min) {
        this.exam15Min = exam15Min;
    }

    public Double getExam45Min() {
        return exam45Min;
    }

    public void setExam45Min(Double exam45Min) {
        this.exam45Min = exam45Min;
    }

    public Double getExamMiddle() {
        return examMiddle;
    }

    public void setExamMiddle(Double examMiddle) {
        this.examMiddle = examMiddle;
    }

    public Double getExamFinal() {
        return examFinal;
    }

    public void setExamFinal(Double examFinal) {
        this.examFinal = examFinal;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
