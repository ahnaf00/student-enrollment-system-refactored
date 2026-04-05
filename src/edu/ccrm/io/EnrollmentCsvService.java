package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import edu.ccrm.service.DataStore;
import edu.ccrm.util.RecursiveFileUtils;

public class EnrollmentCsvService {
    private final DataStore dataStore;
    private final Path dataDir;
    
    public EnrollmentCsvService(DataStore dataStore, Path dataDir) {
        this.dataStore = dataStore;
        this.dataDir = dataDir;
    }
    
    public void exportEnrollments() throws IOException {
        RecursiveFileUtils.ensureDirectoryExists(dataDir);
        
        Path enrollmentsFile = dataDir.resolve("enrollments_export.csv");
        
        List<String> lines = dataStore.getAllStudents().stream()
            .flatMap(student -> student.getEnrolledCourses().stream()
                .map(en -> String.format("%s,%s,%s",
                		student.getRegNo(),
                    en.getCourse().getCourseCode().getFullCode(),
                    en.getGrade())))
            .collect(Collectors.toList());
        
        lines.add(0, "regNo,courseCode,grade");
        Files.write(enrollmentsFile, lines);
    }
}