package edu.ccrm.service.strategy;

import edu.ccrm.domain.Grade;

/**
 * Different grading policies can be swapped without changing the EnrollmentService.
 */
public interface GradingStrategy {
    Grade determineGrade(int marks);
}
