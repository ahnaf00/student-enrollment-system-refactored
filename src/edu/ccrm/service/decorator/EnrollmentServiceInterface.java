package edu.ccrm.service.decorator;

import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;

/**
 * Both the real service and decorators implement this interface.
 */
public interface EnrollmentServiceInterface {
    void enrollStudent(String regNo, String courseCode)
            throws MaxCreditLimitExceededException, DuplicateEnrollmentException;
    void recordGrade(String regNo, String courseCode, int marks);
}
