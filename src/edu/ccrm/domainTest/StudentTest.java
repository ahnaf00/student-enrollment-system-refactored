package edu.ccrm.domainTest;

import edu.ccrm.domain.*;
import edu.ccrm.domain.Student.StudentStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    private Student student;
    private Course course;
    private Instructor instructor;

    @BeforeEach
    void setUp() {
        student = new Student(1, "REG001", "John Doe", "john@example.com",
                LocalDate.of(2000, 1, 15));

        instructor = new Instructor(10, "Dr. Smith", "smith@example.com",
                LocalDate.of(1975, 3, 20), "CSE", "Room 101");

        course = new Course.Builder(new CourseCode("CSE", 101), "Intro to CS")
                .credits(3)
                .instructor(instructor)
                .semester(Semester.FALL)
                .department("CSE")
                .build();
    }

    // ─────────────────────────────────────────
    // Constructor Tests
    // ─────────────────────────────────────────

    @Test
    void constructor_ShouldCreateStudent_WhenValidInputsGiven() {
        assertNotNull(student);
    }

    @Test
    void constructor_ShouldSetId_Correctly() {
        assertEquals(1, student.getId());
    }

    @Test
    void constructor_ShouldSetRegNo_Correctly() {
        assertEquals("REG001", student.getRegNo());
    }

    @Test
    void constructor_ShouldSetFullName_Correctly() {
        assertEquals("John Doe", student.getFullName());
    }

    @Test
    void constructor_ShouldSetEmail_Correctly() {
        assertEquals("john@example.com", student.getEmail());
    }

    @Test
    void constructor_ShouldSetDateOfBirth_Correctly() {
        assertEquals(LocalDate.of(2000, 1, 15), student.getDateOfBirth());
    }

    @Test
    void constructor_ShouldSetDefaultStatus_AsACTIVE() {
        assertEquals(StudentStatus.ACTIVE, student.getStatus());
    }

    @Test
    void constructor_ShouldInitializeEnrolledCourses_AsEmptyList() {
        assertTrue(student.getEnrolledCourses().isEmpty());
    }

    // ─────────────────────────────────────────
    // getProfile() Tests
    // ─────────────────────────────────────────

    @Test
    void getProfile_ShouldNotReturnNull() {
        assertNotNull(student.getProfile());
    }

    @Test
    void getProfile_ShouldContainRegNo() {
        assertTrue(student.getProfile().contains("REG001"));
    }

    @Test
    void getProfile_ShouldContainFullName() {
        assertTrue(student.getProfile().contains("John Doe"));
    }

    @Test
    void getProfile_ShouldContainEmail() {
        assertTrue(student.getProfile().contains("john@example.com"));
    }

    @Test
    void getProfile_ShouldContainStatus() {
        assertTrue(student.getProfile().contains("ACTIVE"));
    }

    @Test
    void getProfile_ShouldContainHeader() {
        assertTrue(student.getProfile().contains("--- Student Profile ---"));
    }

    // ─────────────────────────────────────────
    // setStatus() / getStatus() Tests
    // ─────────────────────────────────────────

    @Test
    void setStatus_ShouldUpdateStatus_ToINACTIVE() {
        student.setStatus(StudentStatus.INACTIVE);
        assertEquals(StudentStatus.INACTIVE, student.getStatus());
    }

    @Test
    void setStatus_ShouldUpdateStatus_ToGRADUATED() {
        student.setStatus(StudentStatus.GRADUATED);
        assertEquals(StudentStatus.GRADUATED, student.getStatus());
    }

    @Test
    void setStatus_ShouldUpdateStatus_BackToACTIVE() {
        student.setStatus(StudentStatus.INACTIVE);
        student.setStatus(StudentStatus.ACTIVE);
        assertEquals(StudentStatus.ACTIVE, student.getStatus());
    }

    // ─────────────────────────────────────────
    // addEnrollment() Tests
    // ─────────────────────────────────────────

    @Test
    void addEnrollment_ShouldIncreaseEnrollmentListSize() {
        Enrollment enrollment = new Enrollment(student, course);
        student.addEnrollment(enrollment);
        assertEquals(1, student.getEnrolledCourses().size());
    }

    @Test
    void addEnrollment_ShouldContainAddedEnrollment() {
        Enrollment enrollment = new Enrollment(student, course);
        student.addEnrollment(enrollment);
        assertTrue(student.getEnrolledCourses().contains(enrollment));
    }

    @Test
    void addEnrollment_ShouldAllowMultipleEnrollments() {
        Course course2 = new Course.Builder(new CourseCode("MATH", 201), "Calculus")
                .credits(3)
                .instructor(instructor)
                .semester(Semester.SPRING)
                .department("MATH")
                .build();

        student.addEnrollment(new Enrollment(student, course));
        student.addEnrollment(new Enrollment(student, course2));
        assertEquals(2, student.getEnrolledCourses().size());
    }

    // ─────────────────────────────────────────
    // Defensive Copy Test
    // ─────────────────────────────────────────

    @Test
    void getEnrolledCourses_ShouldReturnDefensiveCopy() {
        Enrollment enrollment = new Enrollment(student, course);
        student.addEnrollment(enrollment);

        List<Enrollment> copy = student.getEnrolledCourses();
        copy.clear(); // modify the returned copy

        // original list should still have 1 item
        assertEquals(1, student.getEnrolledCourses().size());
    }

    // ─────────────────────────────────────────
    // Inherited from Person — Setter Tests
    // ─────────────────────────────────────────

    @Test
    void setFullName_ShouldUpdateFullName() {
        student.setFullName("Jane Doe");
        assertEquals("Jane Doe", student.getFullName());
    }

    @Test
    void setEmail_ShouldUpdateEmail() {
        student.setEmail("jane@example.com");
        assertEquals("jane@example.com", student.getEmail());
    }

    @Test
    void setDateOfBirth_ShouldUpdateDateOfBirth() {
        LocalDate newDate = LocalDate.of(1999, 5, 20);
        student.setDateOfBirth(newDate);
        assertEquals(newDate, student.getDateOfBirth());
    }

    // ─────────────────────────────────────────
    // toString() Tests (Inherited from Person)
    // ─────────────────────────────────────────

    @Test
    void toString_ShouldNotReturnNull() {
        assertNotNull(student.toString());
    }

    @Test
    void toString_ShouldContainId() {
        assertTrue(student.toString().contains("1"));
    }

    @Test
    void toString_ShouldContainFullName() {
        assertTrue(student.toString().contains("John Doe"));
    }

    @Test
    void toString_ShouldContainEmail() {
        assertTrue(student.toString().contains("john@example.com"));
    }
}