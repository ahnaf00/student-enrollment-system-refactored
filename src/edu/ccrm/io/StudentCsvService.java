package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.ccrm.domain.Student;
import edu.ccrm.service.DataStore;

public class StudentCsvService {
    private final DataStore dataStore;
    private final Path dataDir;
    
    public StudentCsvService(DataStore dataStore, Path dataDir) {
        this.dataStore = dataStore;
        this.dataDir = dataDir;
    }
    
    public void importStudents() throws IOException {
        Path studentsFile = dataDir.resolve("students.csv");
        
        if (!Files.exists(studentsFile)) {
            return;
        }
        
        try (Stream<String> lines = Files.lines(studentsFile)) {
            lines.skip(1)
                .map(line -> line.split(","))
                .forEach(parts -> {
                    Student student = new Student(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2],
                        parts[3],
                        LocalDate.parse(parts[4])
                    );
                    dataStore.addStudent(student);
                });
        }
    }
    
    public void exportStudents() throws IOException {
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }
        
        Path studentsFile = dataDir.resolve("students_export.csv");
        
        List<String> lines = dataStore.getAllStudents().stream()
            .map(s -> String.format("%d,%s,%s,%s,%s,%s",
                s.getId(), s.getRegNo(), s.getFullName(),
                s.getEmail(), s.getDateOfBirth(), s.getStatus()))
            .collect(Collectors.toList());
        
        lines.add(0, "id,regNo,fullName,email,dob,status");
        Files.write(studentsFile, lines);
    }
}