package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.CourseCode;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import edu.ccrm.service.DataStore;

public class ImportExportService {
    private final StudentCsvService studentCsvService;
    private final CourseCsvService courseCsvService;
    private final EnrollmentCsvService enrollmentCsvService;
    private final InstructorSetupService instructorSetupService;
    
    public ImportExportService(DataStore dataStore) {
        Path dataDir = Paths.get(AppConfig.getInstance().getDataDirectory());
        
        this.studentCsvService = new StudentCsvService(dataStore, dataDir);
        this.courseCsvService = new CourseCsvService(dataStore, dataDir);
        this.enrollmentCsvService = new EnrollmentCsvService(dataStore, dataDir);
        this.instructorSetupService = new InstructorSetupService(dataStore);
    }

    public void importData() {
        try {
            Path dataDir = Paths.get(AppConfig.getInstance().getDataDirectory());
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }

            instructorSetupService.initializeInstructors();
            courseCsvService.importCourses();
            studentCsvService.importStudents();
            
            System.out.println("Data imported successfully from '" + dataDir + "' directory.");
        } catch (IOException e) {
            System.err.println("Error during data import: " + e.getMessage());
        }
    }

    public void exportData() {
        try {
            studentCsvService.exportStudents();
            courseCsvService.exportCourses();
            enrollmentCsvService.exportEnrollments();
            
            Path dataDir = Paths.get(AppConfig.getInstance().getDataDirectory());
            System.out.println("Data exported successfully to '" + dataDir + "' directory.");
        } catch (IOException e) {
            System.err.println("Error during data export: " + e.getMessage());
        }
    }
}