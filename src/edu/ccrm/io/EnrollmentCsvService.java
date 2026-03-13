package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import edu.ccrm.service.DataStore;

public class EnrollmentCsvService {
    private final DataStore dataStore;
    private final Path dataDir;
    
    public EnrollmentCsvService(DataStore dataStore, Path dataDir) {
        this.dataStore = dataStore;
        this.dataDir = dataDir;
    }
    
    public void exportEnrollments() throws IOException {
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }
        
        Path enrollmentsFile = dataDir.resolve("enrollments_export.csv");
        
        List<String> lines = dataStore.getAllStudents().stream()
            .flatMap(s -> s.getEnrolledCourses().stream()
                .map(en -> String.format("%s,%s,%s",
                    s.getRegNo(),
                    en.getCourse().getCourseCode().getFullCode(),
                    en.getGrade())))
            .collect(Collectors.toList());
        
        lines.add(0, "regNo,courseCode,grade");
        Files.write(enrollmentsFile, lines);
    }
}