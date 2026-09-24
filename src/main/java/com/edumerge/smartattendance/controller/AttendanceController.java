package com.edumerge.smartattendance.controller;

import com.edumerge.smartattendance.dto.AttendanceCorrectionRequest;
import com.edumerge.smartattendance.dto.AttendanceRequest;
import com.edumerge.smartattendance.dto.AttendanceSummary;
import com.edumerge.smartattendance.entity.Attendance;
import com.edumerge.smartattendance.entity.AttendanceCorrection;
import com.edumerge.smartattendance.service.AttendanceService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // 1. Mark attendance
    @PostMapping("/mark/{subjectId}")
    public List<Attendance> markAttendance(
            @PathVariable Long subjectId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            @RequestBody List<AttendanceRequest> requests) {

        return attendanceService.markAttendance(
                subjectId, date, requests);
    }

    // 2. Get attendance by subject
    @GetMapping("/subject/{subjectId}")
    public List<Attendance> getAttendanceBySubject(
            @PathVariable Long subjectId) {

        return attendanceService.getAttendanceBySubject(subjectId);
    }

    // 3. Get attendance by student
    @GetMapping("/student/{studentId}")
    public List<Attendance> getAttendanceByStudent(
            @PathVariable Long studentId) {

        return attendanceService.getAttendanceByStudent(studentId);
    }

    // 4. Get attendance by subject and date
    @GetMapping("/subject/{subjectId}/date")
    public List<Attendance> getAttendanceBySubjectAndDate(
            @PathVariable Long subjectId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return attendanceService.getAttendanceBySubjectAndDate(
                subjectId, date);
    }

    // 5. Get attendance summary for a student
    @GetMapping("/summary")
    public AttendanceSummary getAttendanceSummary(
            @RequestParam Long studentId,
            @RequestParam Long subjectId) {

        return attendanceService.getAttendanceSummary(
                studentId, subjectId);
    }

    // 6. Get students with low attendance
    @GetMapping("/low-attendance/{subjectId}")
    public List<AttendanceSummary> getLowAttendanceStudents(
            @PathVariable Long subjectId,
            @RequestParam(defaultValue = "75") double threshold) {

        return attendanceService.getLowAttendanceStudents(
                subjectId, threshold);
    }

    // 7. Correct attendance
    @PatchMapping("/{attendanceId}/correct")
    public Attendance correctAttendance(
            @PathVariable Long attendanceId,
            @RequestBody AttendanceCorrectionRequest request) {

        return attendanceService.correctAttendance(
                attendanceId, request);
    }

    // 8. Get attendance correction history
    @GetMapping("/{attendanceId}/history")
    public List<AttendanceCorrection> getCorrectionHistory(
            @PathVariable Long attendanceId) {

        return attendanceService.getCorrectionHistory(attendanceId);
    }
}