package edu.ccrm.service.observer;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;


public class EnrollmentNotifier implements EnrollmentObserver {
    @Override
    public void onEnrollment(Student student, Course course) {
        System.out.printf("[NOTIFICATION] Dear %s, you have been successfully enrolled in '%s'.%n",
                student.getFullName(), course.getTitle());
    }

    @Override
    public void onGradeRecorded(Student student, Course course, String grade) {
        System.out.printf("[NOTIFICATION] Dear %s, your grade for '%s' has been updated to: %s%n",
                student.getFullName(), course.getTitle(), grade);
    }
}
