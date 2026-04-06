package edu.ccrm.io.adapter;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;

/**
 * Implements ExportFormatAdapter to provide CSV-specific formatting.
 */
public class CsvExportAdapter implements ExportFormatAdapter {

    @Override
    public String formatStudentRecord(Student student) {
        return String.format("%d,%s,%s,%s,%s,%s",
                student.getId(), student.getRegNo(), student.getFullName(),
                student.getEmail(), student.getDateOfBirth(), student.getStatus());
    }

    @Override
    public String formatEnrollmentRecord(Student student, Enrollment enrollment) {
        return String.format("%s,%s,%s",
                student.getRegNo(),
                enrollment.getCourse().getCourseCode().getFullCode(),
                enrollment.getGrade());
    }

    @Override
    public String getStudentHeader() {
        return "id,regNo,fullName,email,dob,status";
    }

    @Override
    public String getEnrollmentHeader() {
        return "regNo,courseCode,grade";
    }
}
