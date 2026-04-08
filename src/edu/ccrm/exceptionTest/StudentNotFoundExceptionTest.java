package edu.ccrm.exceptionTest;

import edu.ccrm.exception.StudentNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentNotFoundExceptionTest {

    // ─────────────────────────────────────────
    // Constructor Tests
    // ─────────────────────────────────────────

    @Test
    void constructor_ShouldCreateException_WhenMessageGiven() {
        StudentNotFoundException ex = new StudentNotFoundException("Student not found");
        assertNotNull(ex);
    }

    @Test
    void constructor_ShouldSetMessage_Correctly() {
        StudentNotFoundException ex = new StudentNotFoundException("Student not found");
        assertEquals("Student not found", ex.getMessage());
    }

    @Test
    void constructor_ShouldSetCustomMessage_Correctly() {
        StudentNotFoundException ex = new StudentNotFoundException("ID: 101 not found");
        assertEquals("ID: 101 not found", ex.getMessage());
    }

    // ─────────────────────────────────────────
    // Exception Type Tests
    // ─────────────────────────────────────────

    @Test
    void exception_ShouldBeInstanceOf_RuntimeException() {
        StudentNotFoundException ex = new StudentNotFoundException("Student not found");
        assertInstanceOf(RuntimeException.class, ex);
    }

    @Test
    void exception_ShouldBeInstanceOf_StudentNotFoundException() {
        StudentNotFoundException ex = new StudentNotFoundException("Student not found");
        assertInstanceOf(StudentNotFoundException.class, ex);
    }

    // ─────────────────────────────────────────
    // Throw / Catch Tests
    // ─────────────────────────────────────────

    @Test
    void exception_ShouldBeThrowable() {
        assertThrows(StudentNotFoundException.class, () -> {
            throw new StudentNotFoundException("Student not found");
        });
    }

    @Test
    void exception_ShouldBeCatchable_AsRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            throw new StudentNotFoundException("Student not found");
        });
    }

    @Test
    void exception_ShouldContainCorrectMessage_WhenThrown() {
        StudentNotFoundException ex = assertThrows(StudentNotFoundException.class, () -> {
            throw new StudentNotFoundException("Student ID 5 not found");
        });
        assertEquals("Student ID 5 not found", ex.getMessage());
    }
}