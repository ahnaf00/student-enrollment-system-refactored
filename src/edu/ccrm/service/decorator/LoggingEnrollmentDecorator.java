package edu.ccrm.service.decorator;

import java.time.LocalDateTime;

import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;

/**
 * Wraps any EnrollmentServiceInterface without modifying the original class.
 */
public class LoggingEnrollmentDecorator extends EnrollmentServiceDecorator {

    public LoggingEnrollmentDecorator(EnrollmentServiceInterface wrapped) {
        super(wrapped);
    }

    @Override
    public void enrollStudent(String regNo, String courseCode)
            throws MaxCreditLimitExceededException, DuplicateEnrollmentException {
        System.out.println("[LOG " + LocalDateTime.now() + "] Attempting enrollment: "
                + regNo + " -> " + courseCode);
        wrapped.enrollStudent(regNo, courseCode);
        System.out.println("[LOG " + LocalDateTime.now() + "] Enrollment completed: "
                + regNo + " -> " + courseCode);
    }

    @Override
    public void recordGrade(String regNo, String courseCode, int marks) {
        System.out.println("[LOG " + LocalDateTime.now() + "] Recording grade: "
                + regNo + " in " + courseCode + " = " + marks);
        wrapped.recordGrade(regNo, courseCode, marks);
        System.out.println("[LOG " + LocalDateTime.now() + "] Grade recorded successfully");
    }
}
