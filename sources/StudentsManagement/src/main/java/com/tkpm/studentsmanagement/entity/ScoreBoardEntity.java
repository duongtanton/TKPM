package com.tkpm.studentsmanagement.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * @author : daitt
 * @create : 5/6/2023
 **/

@Entity(name = "score_board")
@EntityListeners(AuditingEntityListener.class)
public class ScoreBoardEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentId")
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classId")
    private ClassEntity classs;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subjectId")
    private SubjectEntity subject;

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
    private Integer semester;

    @Column(name = "year")
    private Integer year;

    @Column(name = "isCompleted")
    private Boolean isCompleted;

    // relationship
    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "scoreboard_student", joinColumns = @JoinColumn(name = "scoreboardId"), inverseJoinColumns = @JoinColumn(name = "studentID"))
    // private List<StudentEntity> students;

    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "scoreboard_class", joinColumns = @JoinColumn(name = "scoreboardId"), inverseJoinColumns = @JoinColumn(name = "classId"))
    // private List<ClassEntity> classes;

    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "scoreboard_subject", joinColumns = @JoinColumn(name = "scoreboardId"), inverseJoinColumns = @JoinColumn(name = "subjectId"))
    // private List<ClassEntity> subjects;
    // end relationship

    public StudentEntity getStudent() {
        return this.student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public ClassEntity getClasss() {
        return this.classs;
    }

    public void setClasss(ClassEntity classs) {
        this.classs = classs;
    }

    public SubjectEntity getSubject() {
        return this.subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
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

    // public List<StudentEntity> getStudents() {
    //     return this.students;
    // }

    // public void setStudents(List<StudentEntity> students) {
    //     this.students = students;
    // }

    // public List<ClassEntity> getClasses() {
    //     return this.classes;
    // }

    // public void setClasses(List<ClassEntity> classes) {
    //     this.classes = classes;
    // }

    // public List<ClassEntity> getSubjects() {
    //     return this.subjects;
    // }

    // public void setSubjects(List<ClassEntity> subjects) {
    //     this.subjects = subjects;
    // }

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

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
}
