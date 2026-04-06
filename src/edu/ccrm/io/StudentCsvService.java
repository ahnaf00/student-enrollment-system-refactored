package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.ccrm.domain.Student;
import edu.ccrm.factory.StudentFactory;
import edu.ccrm.io.adapter.CsvExportAdapter;
import edu.ccrm.io.adapter.ExportFormatAdapter;
import edu.ccrm.service.proxy.DataStoreInterface;
import edu.ccrm.util.RecursiveFileUtils;


public class StudentCsvService {
    private final DataStoreInterface dataStore;
    private final Path dataDir;
    private final StudentFactory studentFactory = new StudentFactory();
    private final ExportFormatAdapter formatAdapter = new CsvExportAdapter();
    
    public StudentCsvService(DataStoreInterface dataStore, Path dataDir) {
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
                    Student student = studentFactory.createPerson(
                        Integer.parseInt(parts[0]),
                        parts[2], parts[3],
                        LocalDate.parse(parts[4]),
                        parts[1]
                    );
                    dataStore.addStudent(student);
                });
        }
    }
    
    public void exportStudents() throws IOException {
        RecursiveFileUtils.ensureDirectoryExists(dataDir);
        
        Path studentsFile = dataDir.resolve("students_export.csv");
        
        List<String> lines = dataStore.getAllStudents().stream()
            .map(student -> formatAdapter.formatStudentRecord(student))
            .collect(Collectors.toList());
        
        lines.add(0, formatAdapter.getStudentHeader());
        Files.write(studentsFile, lines);
    }
}