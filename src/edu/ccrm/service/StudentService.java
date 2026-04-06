package edu.ccrm.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ccrm.domain.Student;
import edu.ccrm.exception.StudentNotFoundException;
import edu.ccrm.service.proxy.DataStoreInterface;
import edu.ccrm.util.TranscriptFormatter;


public class StudentService {
    private final DataStoreInterface dataStore;
    private final TranscriptFormatter transcriptFormatter;

    public StudentService(DataStoreInterface dataStore) {
        this.dataStore = dataStore;
        this.transcriptFormatter = new TranscriptFormatter();
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

    public String getStudentTranscript(String regNo) {
        Student student = findStudentByRegNo(regNo);
        return transcriptFormatter.format(student);
    }
}