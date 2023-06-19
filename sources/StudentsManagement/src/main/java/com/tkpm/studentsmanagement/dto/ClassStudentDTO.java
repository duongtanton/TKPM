package com.tkpm.studentsmanagement.dto;

import java.sql.Date;

public class ClassStudentDTO extends AbstractDTO{
    private Long classId;
    private Long studentId;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}

