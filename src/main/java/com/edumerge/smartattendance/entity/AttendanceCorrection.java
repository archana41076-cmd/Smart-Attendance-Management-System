package com.edumerge.smartattendance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_corrections")
public class AttendanceCorrection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "attendance_id", nullable = false)
    private Attendance attendance;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus oldStatus;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus newStatus;

    private String reason;

    private String correctedBy;

    private LocalDateTime correctedAt;

    public AttendanceCorrection() {
    }

    public Long getId() {
        return id;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public AttendanceStatus getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(AttendanceStatus oldStatus) {
        this.oldStatus = oldStatus;
    }

    public AttendanceStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(AttendanceStatus newStatus) {
        this.newStatus = newStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCorrectedBy() {
        return correctedBy;
    }

    public void setCorrectedBy(String correctedBy) {
        this.correctedBy = correctedBy;
    }

    public LocalDateTime getCorrectedAt() {
        return correctedAt;
    }

    public void setCorrectedAt(LocalDateTime correctedAt) {
        this.correctedAt = correctedAt;
    }
}