package com.tkpm.studentsmanagement.entity;

import com.tkpm.studentsmanagement.constant.ReportType;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "report")
@EntityListeners(AuditingEntityListener.class)
public class ReportEntity extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classId")
    private ClassEntity classs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectId")
    private SubjectEntity subject;

    @Enumerated(value = EnumType.STRING)
    @JoinColumn(name = "type")
    private ReportType type;

    @Column(name = "year")
    private Integer year;

    @Column(name = "semester")
    private Integer semester;

    @Column(name = "averageScore")
    private Double averageScore;

    @Column(name = "numberOfPupils")
    private Integer numberOfPupils;

    @Column(name = "passQuantity")
    private Integer passQuantity;

    @Column(name = "passPercent")
    private Double passPercent;

    public ClassEntity getClasss() {
        return classs;
    }

    public void setClasss(ClassEntity classs) {
        this.classs = classs;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getNumberOfPupils() {
        return numberOfPupils;
    }

    public void setNumberOfPupils(Integer numberOfPupils) {
        this.numberOfPupils = numberOfPupils;
    }

    public Integer getPassQuantity() {
        return passQuantity;
    }

    public void setPassQuantity(Integer passQuantity) {
        this.passQuantity = passQuantity;
    }

    public Double getPassPercent() {
        return passPercent;
    }

    public void setPassPercent(Double passPercent) {
        this.passPercent = passPercent;
    }
}
