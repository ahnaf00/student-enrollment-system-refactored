package edu.ccrm.exceptionTest;

import edu.ccrm.exception.MaxCreditLimitExceededException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MaxCreditLimitExceededExceptionTest {

    // ─────────────────────────────────────────
    // Constructor Tests
    // ─────────────────────────────────────────

    @Test
    void constructor_ShouldCreateException_WhenMessageGiven() {
        MaxCreditLimitExceededException ex = new MaxCreditLimitExceededException("Max credit limit exceeded");
        assertNotNull(ex);
    }

    @Test
    void constructor_ShouldSetMessage_Correctly() {
        MaxCreditLimitExceededException ex = new MaxCreditLimitExceededException("Max credit limit exceeded");
        assertEquals("Max credit limit exceeded", ex.getMessage());
    }

    @Test
    void constructor_ShouldSetCustomMessage_Correctly() {
        MaxCreditLimitExceededException ex = new MaxCreditLimitExceededException("Cannot exceed 20 credits");
        assertEquals("Cannot exceed 20 credits", ex.getMessage());
    }

    // ─────────────────────────────────────────
    // Exception Type Tests
    // ─────────────────────────────────────────

    @Test
    void exception_ShouldBeInstanceOf_Exception() {
        MaxCreditLimitExceededException ex = new MaxCreditLimitExceededException("Max credit limit exceeded");
        assertInstanceOf(Exception.class, ex);
    }

    @Test
    void exception_ShouldBeInstanceOf_MaxCreditLimitExceededException() {
        MaxCreditLimitExceededException ex = new MaxCreditLimitExceededException("Max credit limit exceeded");
        assertInstanceOf(MaxCreditLimitExceededException.class, ex);
    }


    // ─────────────────────────────────────────
    // Throw / Catch Tests
    // ─────────────────────────────────────────

    @Test
    void exception_ShouldBeThrowable() {
        assertThrows(MaxCreditLimitExceededException.class, () -> {
            throw new MaxCreditLimitExceededException("Max credit limit exceeded");
        });
    }

    @Test
    void exception_ShouldBeCatchable_AsException() {
        assertThrows(Exception.class, () -> {
            throw new MaxCreditLimitExceededException("Max credit limit exceeded");
        });
    }

    @Test
    void exception_ShouldContainCorrectMessage_WhenThrown() {
        MaxCreditLimitExceededException ex = assertThrows(MaxCreditLimitExceededException.class, () -> {
            throw new MaxCreditLimitExceededException("Cannot exceed 20 credits");
        });
        assertEquals("Cannot exceed 20 credits", ex.getMessage());
    }
}
