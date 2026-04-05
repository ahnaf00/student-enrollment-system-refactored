package edu.ccrm.util;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;

/**
 * Single source of truth for GPA calculation.
 * Eliminates duplication between ReportService and TranscriptFormatter.
 */
public class GpaCalculator {

    private static final double VALID_GRADE_THRESHOLD = 0.0;

    public static double calculate(Student student) {
        double totalPoints = 0;
        int totalCredits = 0;
        for (Enrollment enrollment : student.getEnrolledCourses()) {
            if (enrollment.getGrade().getGradePoint() >= VALID_GRADE_THRESHOLD) {
                totalPoints += enrollment.getGrade().getGradePoint()
                              * enrollment.getCourse().getCredits();
                totalCredits += enrollment.getCourse().getCredits();
            }
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }
}
