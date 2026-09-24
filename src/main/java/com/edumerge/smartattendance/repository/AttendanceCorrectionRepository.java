package com.edumerge.smartattendance.repository;

import com.edumerge.smartattendance.entity.AttendanceCorrection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceCorrectionRepository
        extends JpaRepository<AttendanceCorrection, Long> {

    List<AttendanceCorrection> findByAttendanceIdOrderByCorrectedAtDesc(
            Long attendanceId);
}