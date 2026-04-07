package edu.ccrm.io.strategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import edu.ccrm.repository.ICourseRepository;
import edu.ccrm.util.FileSystemUtils;

public class CourseCsvExporter implements ICsvExporter {
    private final ICourseRepository courseRepo;
    private final Path dataDir;

    public CourseCsvExporter(ICourseRepository courseRepo, Path dataDir) {
        this.courseRepo = courseRepo;
        this.dataDir = dataDir;
    }

    @Override
    public void export() throws IOException {
        FileSystemUtils.ensureDirectoryExists(dataDir);

        Path filePath = dataDir.resolve("courses_export.csv");

        List<String> lines = courseRepo.findAll().stream()
                .map(course -> String.format("%s,%s,%d,%s,%d,%s",
                        course.getCourseCode().getFullCode(), course.getTitle(),
                        course.getCredits(), course.getSemester(),
                        course.getInstructor().getId(), course.getDepartment()))
                .collect(Collectors.toList());

        lines.add(0, "courseCode,title,credits,semester,instructorId,department");
        Files.write(filePath, lines);
    }
}