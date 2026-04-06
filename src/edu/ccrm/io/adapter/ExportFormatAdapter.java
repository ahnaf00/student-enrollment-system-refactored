package edu.ccrm.io.adapter;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;

/**
 * Decouples data format (CSV/JSON/XML) from the export logic.
 */
public interface ExportFormatAdapter {
    String formatStudentRecord(Student student);
    String formatEnrollmentRecord(Student student, Enrollment enrollment);
    String getStudentHeader();
    String getEnrollmentHeader();
}
