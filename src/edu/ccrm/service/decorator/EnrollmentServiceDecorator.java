package edu.ccrm.service.decorator;

import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;

/**
 * Concrete decorators extend this to add behavior (logging, notifications, etc.).
 */
public abstract class EnrollmentServiceDecorator implements EnrollmentServiceInterface {
    protected final EnrollmentServiceInterface wrapped;

    public EnrollmentServiceDecorator(EnrollmentServiceInterface wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void enrollStudent(String regNo, String courseCode)
            throws MaxCreditLimitExceededException, DuplicateEnrollmentException {
        wrapped.enrollStudent(regNo, courseCode);
    }

    @Override
    public void recordGrade(String regNo, String courseCode, int marks) {
        wrapped.recordGrade(regNo, courseCode, marks);
    }
}
