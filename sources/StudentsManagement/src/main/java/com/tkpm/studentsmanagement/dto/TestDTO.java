package com.tkpm.studentsmanagement.dto;

import java.util.List;

public class TestDTO extends AbstractDTO {
    private Integer type; // using TestType

    private Float score;

    // relationship
    private List<StudentDTO> students;

    private List<ClassDTO> classes;
    // end relationship

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Float getScore() {
        return this.score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public List<StudentDTO> getStudents() {
        return this.students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }

    public List<ClassDTO> getClasses() {
        return this.classes;
    }

    public void setClasses(List<ClassDTO> classes) {
        this.classes = classes;
    }

}
