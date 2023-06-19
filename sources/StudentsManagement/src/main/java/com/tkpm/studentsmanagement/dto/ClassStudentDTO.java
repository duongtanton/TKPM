package com.tkpm.studentsmanagement.dto;

public class ClassStudentDTO extends AbstractDTO {
    private ClassDTO classs;
    private StudentDTO student;

    public ClassDTO getClasss() {
        return this.classs;
    }

    public void setClasss(ClassDTO classs) {
        this.classs = classs;
    }

    public StudentDTO getStudent() {
        return this.student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }
}
