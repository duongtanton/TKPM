package com.tkpm.studentsmanagement.dto;

import java.util.List;

public class ClassDTO extends AbstractDTO {
    private String name;

    private Integer number_of_pupils;

    private String school_year;

    // relationship
    private List<StudentDTO> students;

    private List<TestDTO> tests;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfPupils() {
        return this.number_of_pupils;
    }

    public void setNumberOfPupils(Integer number_of_pupils) {
        this.number_of_pupils = number_of_pupils;
    }

    public String getSchoolYear() {
        return school_year;
    }

    public void setSchoolYear(String school_year) {
        this.school_year = school_year;
    }

    public List<StudentDTO> getStudents() {
        return this.students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }

    public List<TestDTO> getTests() {
        return this.tests;
    }

    public void setTests(List<TestDTO> tests) {
        this.tests = tests;
    }
}
