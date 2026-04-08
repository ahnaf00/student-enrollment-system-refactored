package edu.ccrm.domainTest;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.CourseCode;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    private Course course;
    private Instructor instructor;
    private CourseCode courseCode;

    @BeforeEach
    void setUp() {
        courseCode = new CourseCode("CSE", 101);
        instructor = new Instructor(1, "Dr. Smith", "smith@example.com",
                LocalDate.of(1975, 3, 20), "CSE", "Room 101");

        course = new Course.Builder(courseCode, "Intro to CS")
                .credits(3)
                .instructor(instructor)
                .semester(Semester.FALL)
                .department("CSE")
                .build();
    }

    // ─────────────────────────────────────────
    // Builder / Constructor Tests
    // ─────────────────────────────────────────

    @Test
    void builder_ShouldCreateCourse_WhenValidInputsGiven() {
        assertNotNull(course);
    }

    @Test
    void builder_ShouldSetCourseCode_Correctly() {
        assertEquals(courseCode, course.getCourseCode());
    }

    @Test
    void builder_ShouldSetTitle_Correctly() {
        assertEquals("Intro to CS", course.getTitle());
    }

    @Test
    void builder_ShouldSetCredits_Correctly() {
        assertEquals(3, course.getCredits());
    }

    @Test
    void builder_ShouldSetInstructor_Correctly() {
        assertEquals(instructor, course.getInstructor());
    }

    @Test
    void builder_ShouldSetSemester_Correctly() {
        assertEquals(Semester.FALL, course.getSemester());
    }

    @Test
    void builder_ShouldSetDepartment_Correctly() {
        assertEquals("CSE", course.getDepartment());
    }

    @Test
    void builder_ShouldSetActive_TrueByDefault() {
        assertTrue(course.isActive());
    }

    // ─────────────────────────────────────────
    // Builder — Optional Fields Tests
    // ─────────────────────────────────────────

    @Test
    void builder_ShouldCreateCourse_WithoutDepartment() {
        Course c = new Course.Builder(courseCode, "Intro to CS")
                .credits(3)
                .instructor(instructor)
                .semester(Semester.SPRING)
                .build();
        assertNotNull(c);
    }

    @Test
    void builder_ShouldCreateCourse_WithoutInstructor() {
        Course c = new Course.Builder(courseCode, "Intro to CS")
                .credits(3)
                .semester(Semester.SPRING)
                .department("CSE")
                .build();
        assertNotNull(c);
    }

    @Test
    void builder_ShouldCreateCourse_WithDifferentSemester() {
        Course c = new Course.Builder(new CourseCode("MATH", 201), "Calculus")
                .credits(4)
                .instructor(instructor)
                .semester(Semester.SPRING)
                .department("MATH")
                .build();
        assertEquals(Semester.SPRING, c.getSemester());
    }

    @Test
    void builder_ShouldCreateCourse_WithSummerSemester() {
        Course c = new Course.Builder(new CourseCode("PHY", 301), "Physics")
                .credits(3)
                .instructor(instructor)
                .semester(Semester.SUMMER)
                .build();
        assertEquals(Semester.SUMMER, c.getSemester());
    }

    @Test
    void builder_ShouldCreateCourse_WithWinterSemester() {
        Course c = new Course.Builder(new CourseCode("BIO", 401), "Biology")
                .credits(2)
                .instructor(instructor)
                .semester(Semester.WINTER)
                .build();
        assertEquals(Semester.WINTER, c.getSemester());
    }

    // ─────────────────────────────────────────
    // Getter Tests
    // ─────────────────────────────────────────

    @Test
    void getCourseCode_ShouldNotReturnNull() {
        assertNotNull(course.getCourseCode());
    }

    @Test
    void getTitle_ShouldNotReturnNull() {
        assertNotNull(course.getTitle());
    }

    @Test
    void getTitle_ShouldNotBeEmpty() {
        assertFalse(course.getTitle().isEmpty());
    }

    @Test
    void getCredits_ShouldReturnPositiveValue() {
        assertTrue(course.getCredits() > 0);
    }

    @Test
    void getSemester_ShouldNotReturnNull() {
        assertNotNull(course.getSemester());
    }

    @Test
    void getDepartment_ShouldNotReturnNull() {
        assertNotNull(course.getDepartment());
    }

    // ─────────────────────────────────────────
    // setActive() / isActive() Tests
    // ─────────────────────────────────────────

    @Test
    void setActive_ShouldSetActive_ToFalse() {
        course.setActive(false);
        assertFalse(course.isActive());
    }

    @Test
    void setActive_ShouldSetActive_BackToTrue() {
        course.setActive(false);
        course.setActive(true);
        assertTrue(course.isActive());
    }

    // ─────────────────────────────────────────
    // toString() Tests
    // ─────────────────────────────────────────

    @Test
    void toString_ShouldNotReturnNull() {
        assertNotNull(course.toString());
    }

    @Test
    void toString_ShouldContainCourseCode() {
        assertTrue(course.toString().contains("CSE101"));
    }

    @Test
    void toString_ShouldContainTitle() {
        assertTrue(course.toString().contains("Intro to CS"));
    }

    @Test
    void toString_ShouldContainInstructorName() {
        assertTrue(course.toString().contains("Dr. Smith"));
    }

    @Test
    void toString_ShouldContainSemester() {
        assertTrue(course.toString().contains("FALL"));
    }

    @Test
    void toString_ShouldContainCredits() {
        assertTrue(course.toString().contains("3"));
    }

    @Test
    void toString_ShouldNotBeEmpty() {
        assertFalse(course.toString().isEmpty());
    }
}
