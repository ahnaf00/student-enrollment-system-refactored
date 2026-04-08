package edu.ccrm.exceptionTest;

import edu.ccrm.exception.CourseNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseNotFoundExceptionTest {

    // ─────────────────────────────────────────
    // Constructor Tests
    // ─────────────────────────────────────────

    @Test
    void constructor_ShouldCreateException_WhenMessageGiven() {
        CourseNotFoundException ex = new CourseNotFoundException("Course not found");
        assertNotNull(ex);
    }

    @Test
    void constructor_ShouldSetMessage_Correctly() {
        CourseNotFoundException ex = new CourseNotFoundException("Course not found");
        assertEquals("Course not found", ex.getMessage());
    }

    @Test
    void constructor_ShouldSetCustomMessage_Correctly() {
        CourseNotFoundException ex = new CourseNotFoundException("CSE101 not found");
        assertEquals("CSE101 not found", ex.getMessage());
    }

    // ─────────────────────────────────────────
    // Exception Type Tests
    // ─────────────────────────────────────────

    @Test
    void exception_ShouldBeInstanceOf_RuntimeException() {
        CourseNotFoundException ex = new CourseNotFoundException("Course not found");
        assertInstanceOf(RuntimeException.class, ex);
    }

    @Test
    void exception_ShouldBeInstanceOf_CourseNotFoundException() {
        CourseNotFoundException ex = new CourseNotFoundException("Course not found");
        assertInstanceOf(CourseNotFoundException.class, ex);
    }

    // ─────────────────────────────────────────
    // Throw / Catch Tests
    // ─────────────────────────────────────────

    @Test
    void exception_ShouldBeThrowable() {
        assertThrows(CourseNotFoundException.class, () -> {
            throw new CourseNotFoundException("Course not found");
        });
    }

    @Test
    void exception_ShouldBeCatchable_AsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            throw new CourseNotFoundException("Course not found");
        });
    }

    @Test
    void exception_ShouldContainCorrectMessage_WhenThrown() {
        CourseNotFoundException ex = assertThrows(CourseNotFoundException.class, () -> {
            throw new CourseNotFoundException("CSE101 not found");
        });
        assertEquals("CSE101 not found", ex.getMessage());
    }
}
