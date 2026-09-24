package com.edumerge.smartattendance.repository;

import com.edumerge.smartattendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    List<Attendance> findBySubjectId(Long subjectId);

    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    List<Attendance> findBySubjectIdAndAttendanceDate(
            Long subjectId, LocalDate attendanceDate);

    List<Attendance> findByStudentIdAndSubjectId(
            Long studentId, Long subjectId);

    Optional<Attendance> findByStudentIdAndSubjectIdAndAttendanceDate(
            Long studentId, Long subjectId, LocalDate attendanceDate);
}