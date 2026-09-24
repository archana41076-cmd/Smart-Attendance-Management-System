package com.edumerge.smartattendance.dto;

import com.edumerge.smartattendance.entity.AttendanceStatus;

public class AttendanceRequest {

    private Long studentId;

    private AttendanceStatus status;

    public AttendanceRequest() {
    }

    public AttendanceRequest(Long studentId, AttendanceStatus status) {
        this.studentId = studentId;
        this.status = status;
    }

    public Long getStudentId() {
        return studentId;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
}