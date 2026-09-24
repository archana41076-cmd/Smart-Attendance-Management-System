package com.edumerge.smartattendance.service;

import com.edumerge.smartattendance.dto.AttendanceCorrectionRequest;
import com.edumerge.smartattendance.dto.AttendanceRequest;
import com.edumerge.smartattendance.dto.AttendanceSummary;
import com.edumerge.smartattendance.entity.Attendance;
import com.edumerge.smartattendance.entity.AttendanceCorrection;
import com.edumerge.smartattendance.entity.AttendanceStatus;
import com.edumerge.smartattendance.entity.Student;
import com.edumerge.smartattendance.entity.Subject;
import com.edumerge.smartattendance.repository.AttendanceCorrectionRepository;
import com.edumerge.smartattendance.repository.AttendanceRepository;
import com.edumerge.smartattendance.repository.StudentRepository;
import com.edumerge.smartattendance.repository.SubjectRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final AttendanceCorrectionRepository correctionRepository;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            StudentRepository studentRepository,
            SubjectRepository subjectRepository,
            AttendanceCorrectionRepository correctionRepository) {

        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.correctionRepository = correctionRepository;
    }

    // 1. Mark attendance for students
    @Transactional
    public List<Attendance> markAttendance(
            Long subjectId,
            LocalDate date,
            List<AttendanceRequest> requests) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                        new RuntimeException("Subject not found"));

        for (AttendanceRequest request : requests) {

            Student student = studentRepository.findById(
                    request.getStudentId())
                    .orElseThrow(() ->
                            new RuntimeException("Student not found"));

            Attendance attendance = attendanceRepository
                    .findByStudentIdAndSubjectIdAndAttendanceDate(
                            student.getId(), subjectId, date)
                    .orElse(new Attendance());

            attendance.setStudent(student);
            attendance.setSubject(subject);
            attendance.setAttendanceDate(date);
            attendance.setStatus(request.getStatus());

            attendanceRepository.save(attendance);
        }

        return attendanceRepository.findBySubjectIdAndAttendanceDate(
                subjectId, date);
    }

    // 2. Get attendance by subject
    public List<Attendance> getAttendanceBySubject(Long subjectId) {
        return attendanceRepository.findBySubjectId(subjectId);
    }

    // 3. Get attendance by student
    public List<Attendance> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    // 4. Get attendance by date
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByAttendanceDate(date);
    }
    public List<Attendance> getAttendanceBySubjectAndDate(
            Long subjectId, LocalDate date) {

        return attendanceRepository.findBySubjectIdAndAttendanceDate(
                subjectId, date);
    }

    // 5. Calculate attendance summary for a student
    public AttendanceSummary getAttendanceSummary(
            Long studentId, Long subjectId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                        new RuntimeException("Subject not found"));

        List<Attendance> records =
                attendanceRepository.findByStudentIdAndSubjectId(
                        studentId, subjectId);

        long present = records.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                .count();

        long absent = records.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.ABSENT)
                .count();

        long total = present + absent;

        double percentage = total == 0
                ? 0.0
                : (present * 100.0) / total;

        AttendanceSummary summary = new AttendanceSummary();

        summary.setStudentName(student.getName());
        summary.setRollNumber(student.getRollNumber());
        summary.setSubjectName(subject.getSubjectName());
        summary.setTotalClasses((int) total);
        summary.setPresentClasses((int) present);
        summary.setAbsentClasses((int) absent);
        summary.setAttendancePercentage(
                Math.round(percentage * 100.0) / 100.0);

        return summary;
    }

    // 6. Find students below the attendance threshold
    public List<AttendanceSummary> getLowAttendanceStudents(
            Long subjectId, double threshold) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                        new RuntimeException("Subject not found"));

        List<Student> students =
                studentRepository.findAll();

        return students.stream()
                .map(student -> getAttendanceSummary(
                        student.getId(), subjectId))
                .filter(summary ->
                        summary.getTotalClasses() > 0
                        && summary.getAttendancePercentage() < threshold)
                .toList();
    }

    // 7. Correct attendance and save correction history
    @Transactional
    public Attendance correctAttendance(
            Long attendanceId,
            AttendanceCorrectionRequest request) {

        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() ->
                        new RuntimeException("Attendance record not found"));

        AttendanceStatus oldStatus = attendance.getStatus();
        AttendanceStatus newStatus = request.getNewStatus();

        if (newStatus == null) {
            throw new RuntimeException("New status is required");
        }

        if (request.getReason() == null
                || request.getReason().isBlank()) {
            throw new RuntimeException("Correction reason is required");
        }

        AttendanceCorrection correction = new AttendanceCorrection();

        correction.setAttendance(attendance);
        correction.setOldStatus(oldStatus);
        correction.setNewStatus(newStatus);
        correction.setReason(request.getReason());
        correction.setCorrectedBy(request.getCorrectedBy());
        correction.setCorrectedAt(LocalDateTime.now());

        correctionRepository.save(correction);

        attendance.setStatus(newStatus);

        return attendanceRepository.save(attendance);
    }

    // 8. Get correction history for an attendance record
    public List<AttendanceCorrection> getCorrectionHistory(
            Long attendanceId) {

        return correctionRepository
                .findByAttendanceIdOrderByCorrectedAtDesc(attendanceId);
    }
}