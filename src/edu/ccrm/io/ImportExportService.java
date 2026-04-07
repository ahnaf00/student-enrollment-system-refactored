package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import edu.ccrm.config.AppConfig;
import edu.ccrm.service.proxy.DataStoreInterface;
import edu.ccrm.util.RecursiveFileUtils;


public class ImportExportService {
    private final Path dataDir;
    private final StudentCsvService studentCsvService;
    private final CourseCsvService courseCsvService;
    private final EnrollmentCsvService enrollmentCsvService;
    private final InstructorSetupService instructorSetupService;
    
    public ImportExportService(DataStoreInterface dataStore) {
        this.dataDir = Paths.get(AppConfig.getInstance().getDataDirectory());
        
        this.studentCsvService = new StudentCsvService(dataStore, dataDir);
        this.courseCsvService = new CourseCsvService(dataStore, dataDir);
        this.enrollmentCsvService = new EnrollmentCsvService(dataStore, dataDir);
        this.instructorSetupService = new InstructorSetupService(dataStore);
    }

    public void importData() {
        try {
            RecursiveFileUtils.ensureDirectoryExists(dataDir);

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
            
            System.out.println("Data exported successfully to '" + dataDir + "' directory.");
        } catch (IOException e) {
            System.err.println("Error during data export: " + e.getMessage());
        }
    }
}