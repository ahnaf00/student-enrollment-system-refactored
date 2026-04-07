package edu.ccrm.io.strategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.CourseCode;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;
import edu.ccrm.repository.ICourseRepository;
import edu.ccrm.repository.IInstructorRepository;

public class CourseCsvImporter implements ICsvImporter {
    private final ICourseRepository courseRepo;
    private final IInstructorRepository instructorRepo;
    private final Path dataDir;
    
    public CourseCsvImporter(ICourseRepository courseRepo, 
                             IInstructorRepository instructorRepo, 
                             Path dataDir) {
        this.courseRepo = courseRepo;
        this.instructorRepo = instructorRepo;
        this.dataDir = dataDir;
    }
    
    @Override
    public void importData() throws IOException {
        Path filePath = dataDir.resolve("courses.csv");
        
        if (!Files.exists(filePath)) {
            return;
        }
        
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.skip(1)
                .map(line -> line.split(","))
                .forEach(parts -> {
                    String deptPrefix = parts[0].replaceAll("\\d", "");
                    int courseNum = Integer.parseInt(parts[0].replaceAll("\\D", ""));
                    CourseCode code = new CourseCode(deptPrefix, courseNum);
                    
                    Instructor instructor = instructorRepo
                        .findById(Integer.parseInt(parts[4]))
                        .orElseThrow(() -> new RuntimeException(
                            "Instructor not found"));
                    
                    Course course = new Course.Builder(code, parts[1])
                        .credits(Integer.parseInt(parts[2]))
                        .semester(Semester.valueOf(parts[3].toUpperCase()))
                        .instructor(instructor)
                        .department(parts[5])
                        .build();
                    
                    courseRepo.save(course);
                });
        }
    }
}