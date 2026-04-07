package edu.ccrm.io.strategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import edu.ccrm.repository.IStudentRepository;
import edu.ccrm.util.FileSystemUtils;

public class EnrollmentCsvExporter implements ICsvExporter {
    private final IStudentRepository studentRepo;
    private final Path dataDir;

    public EnrollmentCsvExporter(IStudentRepository studentRepo, Path dataDir) {
        this.studentRepo = studentRepo;
        this.dataDir = dataDir;
    }

    @Override
    public void export() throws IOException {
        FileSystemUtils.ensureDirectoryExists(dataDir);

        Path filePath = dataDir.resolve("enrollments_export.csv");

        List<String> lines = studentRepo.findAll().stream()
                .flatMap(student -> student.getEnrolledCourses().stream()
                        .map(enrollment -> String.format("%s,%s,%s",
                                student.getRegNo(),
                                enrollment.getCourse().getCourseCode().getFullCode(),
                                enrollment.getGrade())))
                .collect(Collectors.toList());

        lines.add(0, "regNo,courseCode,grade");
        Files.write(filePath, lines);
    }
}