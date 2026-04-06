package edu.ccrm.cli;

import java.util.Scanner;

import edu.ccrm.cli.helpers.CourseManagementHelper;
import edu.ccrm.cli.helpers.DataManagementHelper;
import edu.ccrm.cli.helpers.EnrollmentManagementHelper;
import edu.ccrm.cli.helpers.ReportManagementHelper;
import edu.ccrm.cli.helpers.StudentManagementHelper;
import edu.ccrm.config.AppConfig;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.DataStore;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.ReportService;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.bridge.ConsoleReportRenderer;
import edu.ccrm.service.bridge.ReportRenderer;
import edu.ccrm.service.decorator.EnrollmentServiceInterface;
import edu.ccrm.service.decorator.LoggingEnrollmentDecorator;
import edu.ccrm.service.mediator.EnrollmentMediator;
import edu.ccrm.service.mediator.ServiceMediator;
import edu.ccrm.service.observer.EnrollmentLogger;
import edu.ccrm.service.observer.EnrollmentNotifier;
import edu.ccrm.service.proxy.DataStoreInterface;
import edu.ccrm.service.proxy.DataStoreProxy;
import edu.ccrm.service.strategy.GradingStrategy;
import edu.ccrm.service.strategy.StandardGradingStrategy;
import edu.ccrm.util.InputValidator;


public class CliManager {
    private static final Scanner scanner = new Scanner(System.in);

    private static final DataStoreInterface dataStore = new DataStoreProxy(new DataStore());

    private static final StudentService studentService = new StudentService(dataStore);
    private static final CourseService courseService = new CourseService(dataStore);

    private static final ServiceMediator mediator = new EnrollmentMediator(studentService, courseService);

    private static final GradingStrategy gradingStrategy = new StandardGradingStrategy();

    private static final EnrollmentService baseEnrollmentService =
            new EnrollmentService(mediator, gradingStrategy);

    private static final EnrollmentServiceInterface enrollmentService =
            new LoggingEnrollmentDecorator(baseEnrollmentService);

    private static final ReportRenderer reportRenderer = new ConsoleReportRenderer();
    private static final ReportService reportService = new ReportService(dataStore, reportRenderer);

    private static final ImportExportService importExportService = new ImportExportService(dataStore);
    private static final BackupService backupService = new BackupService();

    private static final StudentManagementHelper studentHelper =
            new StudentManagementHelper(studentService, dataStore);
    private static final CourseManagementHelper courseHelper =
            new CourseManagementHelper(courseService);
    private static final EnrollmentManagementHelper enrollmentHelper =
            new EnrollmentManagementHelper(enrollmentService);
    private static final DataManagementHelper dataHelper =
            new DataManagementHelper(importExportService, backupService);
    private static final ReportManagementHelper reportHelper =
            new ReportManagementHelper(reportService);

    public static void main(String[] args) {
        baseEnrollmentService.addObserver(new EnrollmentLogger());
        baseEnrollmentService.addObserver(new EnrollmentNotifier());

        System.out.println("Welcome to the Student Course & Enrollment Management System!");
        System.out.println("Configuration loaded. Data directory: " + AppConfig.getInstance().getDataDirectory());

        importExportService.importData();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = InputValidator.getInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1 -> studentHelper.handleStudentManagement(scanner);
                case 2 -> courseHelper.handleCourseManagement(scanner);
                case 3 -> enrollmentHelper.handleEnrollmentManagement(scanner);
                case 4 -> dataHelper.handleDataManagement(scanner);
                case 5 -> reportHelper.handleReports(scanner);
                case 6 -> {
                    System.out.println("Exiting application...");
                    printPlatformNote();
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollments & Grades");
        System.out.println("4. Import/Export & Backup");
        System.out.println("5. View Reports");
        System.out.println("6. Exit");
    }

    private static void printPlatformNote() {
        System.out.println("\n--- Java Platform Summary ---");
        System.out.println(" * Java SE (Standard Edition): Core Java platform for desktop and server applications.");
        System.out.println(" * Java EE (Enterprise Edition): Built on SE, adds APIs for large-scale enterprise applications.");
        System.out.println(" * Java ME (Micro Edition): A subset of SE for resource-constrained devices.");
        System.out.println("-----------------------------\n");
    }
}