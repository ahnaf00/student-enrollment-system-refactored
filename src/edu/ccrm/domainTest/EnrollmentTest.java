package edu.ccrm.domainTest;

import edu.ccrm.domain.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentTest {

    private Enrollment enrollment;
    private Student student;
    private Course course;
    private Instructor instructor;

    @BeforeEach
    void setUp() {
        student = new Student(1, "REG001", "John Doe", "john@example.com",
                LocalDate.of(2000, 1, 15));

        instructor = new Instructor(1, "Dr. Smith", "smith@example.com",
                LocalDate.of(1975, 3, 20), "CSE", "Room 101");

        course = new Course.Builder(new CourseCode("CSE", 101), "Intro to CS")
                .credits(3)
                .instructor(instructor)
                .semester(Semester.FALL)
                .department("CSE")
                .build();

        enrollment = new Enrollment(student, course);
    }

    // ─────────────────────────────────────────
    // Constructor Tests
    // ─────────────────────────────────────────

    @Test
    void constructor_ShouldCreateEnrollment_WhenValidInputsGiven() {
        assertNotNull(enrollment);
    }

    @Test
    void constructor_ShouldSetStudent_Correctly() {
        assertEquals(student, enrollment.getStudent());
    }

    @Test
    void constructor_ShouldSetCourse_Correctly() {
        assertEquals(course, enrollment.getCourse());
    }

    @Test
    void constructor_ShouldSetDefaultGrade_AsNA() {
        assertEquals(Grade.NA, enrollment.getGrade());
    }

    @Test
    void constructor_ShouldSetEnrollmentDate_AsNotNull() {
        assertNotNull(enrollment.getEnrollmentDate());
    }

    @Test
    void constructor_ShouldSetEnrollmentDate_BeforeOrEqualNow() {
        assertTrue(enrollment.getEnrollmentDate()
                .isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    // ─────────────────────────────────────────
    // getStudent() Tests
    // ─────────────────────────────────────────

    @Test
    void getStudent_ShouldNotReturnNull() {
        assertNotNull(enrollment.getStudent());
    }

    @Test
    void getStudent_ShouldReturnCorrectStudentId() {
        assertEquals(1, enrollment.getStudent().getId());
    }

    @Test
    void getStudent_ShouldReturnCorrectStudentName() {
        assertEquals("John Doe", enrollment.getStudent().getFullName());
    }

    // ─────────────────────────────────────────
    // getCourse() Tests
    // ─────────────────────────────────────────

    @Test
    void getCourse_ShouldNotReturnNull() {
        assertNotNull(enrollment.getCourse());
    }

    @Test
    void getCourse_ShouldReturnCorrectCourseTitle() {
        assertEquals("Intro to CS", enrollment.getCourse().getTitle());
    }

    @Test
    void getCourse_ShouldReturnCorrectCourseCode() {
        assertEquals("CSE101", enrollment.getCourse().getCourseCode().getFullCode());
    }

    // ─────────────────────────────────────────
    // setGrade() / getGrade() Tests
    // ─────────────────────────────────────────

    @Test
    void setGrade_ShouldUpdateGrade_ToS() {
        enrollment.setGrade(Grade.S);
        assertEquals(Grade.S, enrollment.getGrade());
    }

    @Test
    void setGrade_ShouldUpdateGrade_ToA() {
        enrollment.setGrade(Grade.A);
        assertEquals(Grade.A, enrollment.getGrade());
    }

    @Test
    void setGrade_ShouldUpdateGrade_ToB() {
        enrollment.setGrade(Grade.B);
        assertEquals(Grade.B, enrollment.getGrade());
    }

    @Test
    void setGrade_ShouldUpdateGrade_ToC() {
        enrollment.setGrade(Grade.C);
        assertEquals(Grade.C, enrollment.getGrade());
    }

    @Test
    void setGrade_ShouldUpdateGrade_ToD() {
        enrollment.setGrade(Grade.D);
        assertEquals(Grade.D, enrollment.getGrade());
    }

    @Test
    void setGrade_ShouldUpdateGrade_ToE() {
        enrollment.setGrade(Grade.E);
        assertEquals(Grade.E, enrollment.getGrade());
    }

    @Test
    void setGrade_ShouldUpdateGrade_ToF() {
        enrollment.setGrade(Grade.F);
        assertEquals(Grade.F, enrollment.getGrade());
    }

    @Test
    void setGrade_ShouldUpdateGrade_BackToNA() {
        enrollment.setGrade(Grade.A);
        enrollment.setGrade(Grade.NA);
        assertEquals(Grade.NA, enrollment.getGrade());
    }

    // ─────────────────────────────────────────
    // getEnrollmentDate() Tests
    // ─────────────────────────────────────────

    @Test
    void getEnrollmentDate_ShouldNotReturnNull() {
        assertNotNull(enrollment.getEnrollmentDate());
    }

    @Test
    void getEnrollmentDate_ShouldBeInstanceOfLocalDateTime() {
        assertInstanceOf(LocalDateTime.class, enrollment.getEnrollmentDate());
    }

    @Test
    void getEnrollmentDate_ShouldBeConsistent_WhenCalledMultipleTimes() {
        LocalDateTime first  = enrollment.getEnrollmentDate();
        LocalDateTime second = enrollment.getEnrollmentDate();
        assertEquals(first, second);
    }

    // ─────────────────────────────────────────
    // Multiple Enrollment Tests
    // ─────────────────────────────────────────

    @Test
    void twoEnrollments_ShouldHaveDifferentCourses() {
        Course course2 = new Course.Builder(new CourseCode("MATH", 201), "Calculus")
                .credits(4)
                .instructor(instructor)
                .semester(Semester.SPRING)
                .department("MATH")
                .build();

        Enrollment enrollment2 = new Enrollment(student, course2);
        assertNotEquals(enrollment.getCourse(), enrollment2.getCourse());
    }

    @Test
    void twoEnrollments_ShouldBothHaveDefaultGrade_AsNA() {
        Course course2 = new Course.Builder(new CourseCode("MATH", 201), "Calculus")
                .credits(4)
                .instructor(instructor)
                .semester(Semester.SPRING)
                .department("MATH")
                .build();

        Enrollment enrollment2 = new Enrollment(student, course2);
        assertEquals(Grade.NA, enrollment.getGrade());
        assertEquals(Grade.NA, enrollment2.getGrade());
    }

    @Test
    void twoEnrollments_ShouldHaveIndependentGrades() {
        Course course2 = new Course.Builder(new CourseCode("MATH", 201), "Calculus")
                .credits(4)
                .instructor(instructor)
                .semester(Semester.SPRING)
                .department("MATH")
                .build();

        Enrollment enrollment2 = new Enrollment(student, course2);
        enrollment.setGrade(Grade.A);
        enrollment2.setGrade(Grade.B);

        assertEquals(Grade.A, enrollment.getGrade());
        assertEquals(Grade.B, enrollment2.getGrade());
    }
}