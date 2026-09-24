package com.edumerge.smartattendance.dto;

import com.edumerge.smartattendance.entity.AttendanceStatus;

public class AttendanceCorrectionRequest {

    private AttendanceStatus newStatus;
    private String reason;
    private String correctedBy;

    public AttendanceCorrectionRequest() {
    }

    public AttendanceStatus getNewStatus() {
        return newStatus;
    }

    public String getReason() {
        return reason;
    }

    public String getCorrectedBy() {
        return correctedBy;
    }

    public void setNewStatus(AttendanceStatus newStatus) {
        this.newStatus = newStatus;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setCorrectedBy(String correctedBy) {
        this.correctedBy = correctedBy;
    }
}