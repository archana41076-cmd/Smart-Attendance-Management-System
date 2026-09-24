package com.edumerge.smartattendance.controller;

import com.edumerge.smartattendance.entity.Subject;
import com.edumerge.smartattendance.repository.SubjectRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "*")
public class SubjectController {

    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subject addSubject(@RequestBody Subject subject) {

        if (subjectRepository.existsBySubjectCode(subject.getSubjectCode())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Subject code already exists");
        }

        return subjectRepository.save(subject);
    }

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Subject getSubjectById(@PathVariable Long id) {

        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Subject not found"));
    }
}