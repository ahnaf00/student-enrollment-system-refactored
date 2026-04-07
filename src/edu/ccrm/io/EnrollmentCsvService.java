package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import edu.ccrm.io.adapter.CsvExportAdapter;
import edu.ccrm.io.adapter.ExportFormatAdapter;
import edu.ccrm.service.proxy.DataStoreInterface;
import edu.ccrm.util.RecursiveFileUtils;


public class EnrollmentCsvService {
    private final DataStoreInterface dataStore;
    private final Path dataDir;
    private final ExportFormatAdapter formatAdapter = new CsvExportAdapter();
    
    public EnrollmentCsvService(DataStoreInterface dataStore, Path dataDir) {
        this.dataStore = dataStore;
        this.dataDir = dataDir;
    }
    
    public void exportEnrollments() throws IOException {
        RecursiveFileUtils.ensureDirectoryExists(dataDir);
        
        Path enrollmentsFile = dataDir.resolve("enrollments_export.csv");
        
        List<String> lines = dataStore.getAllStudents().stream()
            .flatMap(student -> student.getEnrolledCourses().stream()
                .map(en -> formatAdapter.formatEnrollmentRecord(student, en)))
            .collect(Collectors.toList());
        
        lines.add(0, formatAdapter.getEnrollmentHeader());
        Files.write(enrollmentsFile, lines);
    }
}