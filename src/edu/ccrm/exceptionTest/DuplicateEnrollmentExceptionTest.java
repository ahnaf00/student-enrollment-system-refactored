package edu.ccrm.exceptionTest;

import edu.ccrm.exception.DuplicateEnrollmentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DuplicateEnrollmentExceptionTest {

    // ─────────────────────────────────────────
    // Constructor Tests
    // ─────────────────────────────────────────

    @Test
    void constructor_ShouldCreateException_WhenMessageGiven() {
        DuplicateEnrollmentException ex = new DuplicateEnrollmentException("Duplicate enrollment");
        assertNotNull(ex);
    }

    @Test
    void constructor_ShouldSetMessage_Correctly() {
        DuplicateEnrollmentException ex = new DuplicateEnrollmentException("Duplicate enrollment");
        assertEquals("Duplicate enrollment", ex.getMessage());
    }

    @Test
    void constructor_ShouldSetCustomMessage_Correctly() {
        DuplicateEnrollmentException ex = new DuplicateEnrollmentException("Student already enrolled in CSE101");
        assertEquals("Student already enrolled in CSE101", ex.getMessage());
    }

    // ─────────────────────────────────────────
    // Exception Type Tests
    // ─────────────────────────────────────────

    @Test
    void exception_ShouldBeInstanceOf_Exception() {
        DuplicateEnrollmentException ex = new DuplicateEnrollmentException("Duplicate enrollment");
        assertInstanceOf(Exception.class, ex);
    }

    @Test
    void exception_ShouldBeInstanceOf_DuplicateEnrollmentException() {
        DuplicateEnrollmentException ex = new DuplicateEnrollmentException("Duplicate enrollment");
        assertInstanceOf(DuplicateEnrollmentException.class, ex);
    }



    // ─────────────────────────────────────────
    // Throw / Catch Tests
    // ─────────────────────────────────────────

    @Test
    void exception_ShouldBeThrowable() {
        assertThrows(DuplicateEnrollmentException.class, () -> {
            throw new DuplicateEnrollmentException("Duplicate enrollment");
        });
    }

    @Test
    void exception_ShouldBeCatchable_AsException() {
        assertThrows(Exception.class, () -> {
            throw new DuplicateEnrollmentException("Duplicate enrollment");
        });
    }

    @Test
    void exception_ShouldContainCorrectMessage_WhenThrown() {
        DuplicateEnrollmentException ex = assertThrows(DuplicateEnrollmentException.class, () -> {
            throw new DuplicateEnrollmentException("Student already enrolled in CSE101");
        });
        assertEquals("Student already enrolled in CSE101", ex.getMessage());
    }
}
