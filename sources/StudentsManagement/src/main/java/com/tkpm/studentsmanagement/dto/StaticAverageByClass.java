package com.tkpm.studentsmanagement.dto;

public class StaticAverageByClass {
    public StudentDTO student;
    public ClassDTO classs;
    public Double scoreI;
    public Double scoreII;

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

    public Double getScoreI() {
        return this.scoreI;
    }

    public void setScoreI(Double scoreI) {
        this.scoreI = scoreI;
    }

    public Double getScoreII() {
        return this.scoreII;
    }

    public void setScoreII(Double scoreII) {
        this.scoreII = scoreII;
    }

}