package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.CourseCode;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;
import edu.ccrm.service.proxy.DataStoreInterface;


public class CourseCsvService {
    private final DataStoreInterface dataStore;
    private final Path dataDir;
    
    public CourseCsvService(DataStoreInterface dataStore, Path dataDir) {
        this.dataStore = dataStore;
        this.dataDir = dataDir;
    }
    
    public void importCourses() throws IOException {
        Path coursesFile = dataDir.resolve("courses.csv");
        
        if (!Files.exists(coursesFile)) {
            return;
        }
        
        try (Stream<String> lines = Files.lines(coursesFile)) {
            lines.skip(1)
                .map(line -> line.split(","))
                .forEach(parts -> {
                    String deptPrefix = parts[0].replaceAll("\\d", "");
                    int courseNum = Integer.parseInt(parts[0].replaceAll("\\D", ""));
                    CourseCode code = new CourseCode(deptPrefix, courseNum);
                    
                    Instructor instructor = dataStore.getInstructor(Integer.parseInt(parts[4]));
                    if (instructor == null) {
                        throw new RuntimeException("Instructor not found with ID: " + parts[4]);
                    }
                    
                    Course course = new Course.Builder(code, parts[1])
                        .credits(Integer.parseInt(parts[2]))
                        .semester(Semester.valueOf(parts[3].toUpperCase()))
                        .instructor(instructor)
                        .department(parts[5])
                        .build();
                    
                    dataStore.addCourse(course);
                });
        }
    }
    
    public void exportCourses() throws IOException {
        List<String> lines = dataStore.getAllCourses().stream()
                .map(course -> String.format("%s,%s,%d,%s,%d,%s",
                        course.getCourseCode().getFullCode(), course.getTitle(),
                        course.getCredits(), course.getSemester(),
                        course.getInstructor().getId(), course.getDepartment()))
                .collect(Collectors.toList());
    }
}