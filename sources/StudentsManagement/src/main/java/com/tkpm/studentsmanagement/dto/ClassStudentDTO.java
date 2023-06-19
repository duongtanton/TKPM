package com.tkpm.studentsmanagement.dto;

public class ClassStudentDTO extends AbstractDTO {
    private ClassDTO classDTO;
    
    private StudentDTO studentDTO;

    public ClassDTO getClassDTO() {
        return this.classDTO;
    }

    public void setClassDTO(ClassDTO classId) {
        this.classDTO = classId;
    }

    public StudentDTO getStudentDTO() {
        return this.studentDTO;
    }

    public void setStudentDTO(StudentDTO studentId) {
        this.studentDTO = studentId;
    }

}
