package com.edumerge.smartattendance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String subjectName;

    @Column(nullable = false, unique = true)
    private String subjectCode;

    private String department;

    private String section;

    private String facultyName;

    public Subject() {
    }

    public Subject(String subjectName, String subjectCode,
                   String department, String section, String facultyName) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.department = department;
        this.section = section;
        this.facultyName = facultyName;
    }

    public Long getId() {
        return id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getDepartment() {
        return department;
    }

    public String getSection() {
        return section;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}