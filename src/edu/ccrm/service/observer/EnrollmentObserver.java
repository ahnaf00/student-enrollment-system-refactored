package edu.ccrm.service.observer;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;

/**
 * Decouples enrollment logic from side effects (logging, notifications, etc.).
 */
public interface EnrollmentObserver {
    void onEnrollment(Student student, Course course);
    void onGradeRecorded(Student student, Course course, String grade);
}
