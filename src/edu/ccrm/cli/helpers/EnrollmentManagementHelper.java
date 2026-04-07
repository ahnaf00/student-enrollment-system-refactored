package edu.ccrm.cli.helpers;

import java.util.Scanner;

import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.service.decorator.EnrollmentServiceInterface;
import edu.ccrm.util.InputValidator;

/**
 * This allows transparent wrapping with LoggingEnrollmentDecorator.
 */
public class EnrollmentManagementHelper {
    private final EnrollmentServiceInterface enrollmentService;
    
    public EnrollmentManagementHelper(EnrollmentServiceInterface enrollmentService) {
        this.enrollmentService = enrollmentService;
    }
    
    public void handleEnrollmentManagement(Scanner scanner) {
        System.out.println("\n--- Enrollment & Grading ---");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. Record Grade for Student");
        
        int choice = InputValidator.getInt(scanner, "Enter choice: ");
        String regNo = InputValidator.getString(scanner, "Enter Student Registration No: ");
        String courseCode = InputValidator.getString(scanner, "Enter Course Code (e.g., CS101): ");
        
        switch (choice) {
            case 1 -> enrollStudent(regNo, courseCode);
            case 2 -> recordGrade(scanner, regNo, courseCode);
            default -> System.out.println("Invalid choice.");
        }
    }
    
    private void enrollStudent(String regNo, String courseCode) {
        try {
            enrollmentService.enrollStudent(regNo, courseCode);
        } catch (MaxCreditLimitExceededException | DuplicateEnrollmentException e) {
            System.err.println("Enrollment Failed: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private void recordGrade(Scanner scanner, String regNo, String courseCode) {
        int marks = InputValidator.getInt(scanner, "Enter marks (0-100): ");
        try {
            enrollmentService.recordGrade(regNo, courseCode, marks);
        } catch (NumberFormatException | NullPointerException e) {
            System.err.println("An unexpected error occurred while processing grades: " + e.getMessage());
        }
    }
}