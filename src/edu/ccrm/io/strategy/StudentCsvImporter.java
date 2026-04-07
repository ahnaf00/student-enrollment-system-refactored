package edu.ccrm.io.strategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.stream.Stream;

import edu.ccrm.domain.Student;
import edu.ccrm.repository.IStudentRepository;

public class StudentCsvImporter implements ICsvImporter {
    private final IStudentRepository studentRepo;
    private final Path dataDir;
    
    public StudentCsvImporter(IStudentRepository studentRepo, Path dataDir) {
        this.studentRepo = studentRepo;
        this.dataDir = dataDir;
    }
    
    @Override
    public void importData() throws IOException {
        Path filePath = dataDir.resolve("students.csv");
        
        if (!Files.exists(filePath)) {
            return;
        }
        
        try (Stream<String> lines = Files.lines(filePath)) {
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
                    studentRepo.save(student);
                });
        }
    }
}