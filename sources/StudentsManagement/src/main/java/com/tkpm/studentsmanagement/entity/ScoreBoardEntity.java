package com.tkpm.studentsmanagement.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

/**
 * @author : daitt
 * @create : 5/6/2023
 **/

@Entity(name = "score_board")
@EntityListeners(AuditingEntityListener.class)
public class ScoreBoardEntity extends AbstractEntity{
    @Column(name = "studentID")
    private Long studentID;

    @Column(name = "classID")
    private Long classID;

    @Column(name = "subjectID")
    private Long subjectID;

    @Column(name = "exam15Min")
    private Double exam15Min;

    @Column(name = "exam45Min")
    private Double exam45Min;

    @Column(name = "examMiddle")
    private Double examMiddle;

    @Column(name = "examFinal")
    private Double examFinal;

    @Column(name = "averageScore")
    private Double averageScore;

    @Column(name = "semester")
    private Double semester;

    @Column(name = "year")
    private String year;

    @Column(name = "isCompleted")
    private Boolean isCompleted;

    //relationship
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "scoreboard_student",
            joinColumns = @JoinColumn(name = "scoreboardId"),
            inverseJoinColumns = @JoinColumn(name = "studentID"))
    private List<StudentEntity> students;

    @ManyToMany
    @JoinTable(name = "scoreboard_class",
            joinColumns = @JoinColumn(name = "scoreboardId"),
            inverseJoinColumns = @JoinColumn(name = "classId"))
    private List<ClassEntity> classes;

    @ManyToMany
    @JoinTable(name = "scoreboard_subject",
            joinColumns = @JoinColumn(name = "scoreboardId"),
            inverseJoinColumns = @JoinColumn(name = "subjectId"))
    private List<ClassEntity> subjects;
    //end relationship

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public Long getClassID() {
        return classID;
    }

    public void setClassID(Long classID) {
        this.classID = classID;
    }

    public Long getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Long subjectID) {
        this.subjectID = subjectID;
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

    public Double getSemester() {
        return semester;
    }

    public void setSemester(Double semester) {
        this.semester = semester;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
