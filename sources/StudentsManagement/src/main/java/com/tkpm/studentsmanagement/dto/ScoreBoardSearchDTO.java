package com.tkpm.studentsmanagement.dto;

/**
 * @author : daitt
 * @create : 5/6/2023
 **/

public class ScoreBoardSearchDTO extends AbstractDTO {
    private Long student;
    private Long classs;
    private Long subject;
    private Integer semester;
    private Integer year;

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public Long getClasss() {
        return classs;
    }

    public void setClasss(Long classs) {
        this.classs = classs;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
