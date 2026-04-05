package edu.ccrm.util;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;

/**
 * Utility class responsible for formatting student transcripts.
 * Extracted from StudentService to follow Single Responsibility Principle.
 */
public class TranscriptFormatter {

    /**
     * Formats a complete academic transcript for a student.
     * 
     * @param student The student whose transcript to format
     * @return Formatted transcript as a string
     */
    public String format(Student student) {
        StringBuilder transcript = new StringBuilder();
        appendStudentProfile(transcript, student);
        appendTranscriptHeader(transcript);
        appendCourseDetails(transcript, student);
        appendGpaSummary(transcript, student);
        return transcript.toString();
    }

    private void appendStudentProfile(StringBuilder transcript, Student student) {
        transcript.append(student.getProfile()).append("\n\n");
    }

    private void appendTranscriptHeader(StringBuilder transcript) {
        transcript.append("--- Academic Transcript ---\n");
    }

    private void appendCourseDetails(StringBuilder transcript, Student student) {
        if (student.getEnrolledCourses().isEmpty()) {
            transcript.append("No courses enrolled yet.\n");
        } else {
            appendCourseTableHeader(transcript);
            appendCourseSeparator(transcript);
            appendCourseRows(transcript, student);
        }
    }

    private void appendCourseTableHeader(StringBuilder transcript) {
        transcript.append(String.format("%-10s | %-30s | %-10s | %s\n",
                "Code", "Course Title", "Credits", "Grade"));
    }

    private void appendCourseSeparator(StringBuilder transcript) {
        transcript.append("---------------------------------------------------------------\n");
    }

    private void appendCourseRows(StringBuilder transcript, Student student) {
        for (Enrollment enrollment : student.getEnrolledCourses()) {
            Course course = enrollment.getCourse();
            transcript.append(String.format("%-10s | %-30s | %-10d | %s\n",
                    course.getCourseCode(), course.getTitle(),
                    course.getCredits(), enrollment.getGrade()));
        }
    }

    private void appendGpaSummary(StringBuilder transcript, Student student) {
        transcript.append("---------------------------------------------------------------\n");
        transcript.append(String.format("GPA: %.2f\n", GpaCalculator.calculate(student)));
    }
}