package edu.ccrm.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ccrm.domain.Student;
import edu.ccrm.exception.StudentNotFoundException;
import edu.ccrm.util.TranscriptFormatter;

public class StudentService {
    private final DataStore dataStore;
    private final TranscriptFormatter transcriptFormatter;  // ✅ New formatter

    public StudentService(DataStore dataStore) {
        this.dataStore = dataStore;
        this.transcriptFormatter = new TranscriptFormatter();  // ✅ Initialize formatter
    }

    public void addStudent(Student student) {
        dataStore.addStudent(student);
    }

    public Student findStudentByRegNo(String regNo) {
        Student student = dataStore.getStudent(regNo);
        if (student == null) {
            throw new StudentNotFoundException("Student with Registration No '" + regNo + "' not found.");
        }
        return student;
    }

    public List<Student> getAllStudents() {
        return dataStore.getAllStudents().stream()
            .sorted(Comparator.comparing(Student::getRegNo))
            .collect(Collectors.toList());
    }

    public void updateStudentStatus(String regNo, Student.StudentStatus status) {
        Student student = findStudentByRegNo(regNo);
        student.setStatus(status);
    }

    // ✅ FIXED: Simple delegation to the formatter
    public String getStudentTranscript(String regNo) {
        Student student = findStudentByRegNo(regNo);
        return transcriptFormatter.format(student);
    }

    // ✅ All formatting methods removed - they now live in TranscriptFormatter
    // ✅ Service class is now focused only on student business logic
}