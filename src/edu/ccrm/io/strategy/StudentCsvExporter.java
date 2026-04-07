package edu.ccrm.io.strategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import edu.ccrm.repository.IStudentRepository;
import edu.ccrm.util.FileSystemUtils;

public class StudentCsvExporter implements ICsvExporter {
    private final IStudentRepository studentRepo;
    private final Path dataDir;

    public StudentCsvExporter(IStudentRepository studentRepo, Path dataDir) {
        this.studentRepo = studentRepo;
        this.dataDir = dataDir;
    }

    @Override
    public void export() throws IOException {
        FileSystemUtils.ensureDirectoryExists(dataDir);

        Path filePath = dataDir.resolve("students_export.csv");

        List<String> lines = studentRepo.findAll().stream()
                .map(student -> String.format("%d,%s,%s,%s,%s,%s",
                        student.getId(), 
                        student.getRegNo(), 
                        student.getFullName(),
                        student.getEmail(), 
                        student.getDateOfBirth(), 
                        student.getStatus()))
                .collect(Collectors.toList());

        lines.add(0, "id,regNo,fullName,email,dob,status");
        Files.write(filePath, lines);
    }
}