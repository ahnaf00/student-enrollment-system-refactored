package serviceTest;

import edu.ccrm.domain.*;
import edu.ccrm.service.DataStore;
import edu.ccrm.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReportServiceTest {

    private ReportService reportService;
    private DataStore dataStore;
    private Student student;
    private Course course;
    private Instructor instructor;

    @BeforeEach
    void setUp() {
        dataStore = new DataStore();
        reportService = new ReportService(dataStore);

        instructor = new Instructor(1, "Dr. Smith", "smith@example.com",
                LocalDate.of(1975, 3, 20), "CSE", "Room 101");

        student = new Student(1, "REG001", "John Doe", "john@example.com",
                LocalDate.of(2000, 1, 15));

        course = new Course.Builder(new CourseCode("CSE", 101), "Intro to CS")
                .credits(3)
                .instructor(instructor)
                .semester(Semester.FALL)
                .department("CSE")
                .build();
    }

    // ─────────────────────────────────────────
    // generateGpaDistributionReport() Tests
    // ─────────────────────────────────────────

    @Test
    void generateGpaDistributionReport_ShouldNotThrow_WhenNoStudents() {
        assertDoesNotThrow(() -> reportService.generateGpaDistributionReport());
    }

    @Test
    void generateGpaDistributionReport_ShouldPrintReport_WhenStudentsExist() {
        dataStore.addStudent(student);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        reportService.generateGpaDistributionReport();

        System.setOut(System.out);
        assertTrue(outContent.toString().contains("GPA Distribution Report"));
    }

    @Test
    void generateGpaDistributionReport_ShouldCategorizeExcellent_WhenHighGpa() {
        Enrollment enrollment = new Enrollment(student, course);
        enrollment.setGrade(Grade.S); // 10.0 GPA → Excellent
        student.addEnrollment(enrollment);
        dataStore.addStudent(student);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        reportService.generateGpaDistributionReport();

        System.setOut(System.out);
        assertTrue(outContent.toString().contains("Excellent"));
    }

    @Test
    void generateGpaDistributionReport_ShouldCategorizeBelow_WhenLowGpa() {
        Enrollment enrollment = new Enrollment(student, course);
        enrollment.setGrade(Grade.F); // 0.0 GPA → Below Average
        student.addEnrollment(enrollment);
        dataStore.addStudent(student);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        reportService.generateGpaDistributionReport();

        System.setOut(System.out);
        assertTrue(outContent.toString().contains("Below Average"));
    }

    @Test
    void generateGpaDistributionReport_ShouldHandleStudent_WithNoEnrollments() {
        dataStore.addStudent(student);
        assertDoesNotThrow(() -> reportService.generateGpaDistributionReport());
    }
}