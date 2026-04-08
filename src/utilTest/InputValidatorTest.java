package utilTest;

import edu.ccrm.util.InputValidator;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class InputValidatorTest {

    // ─────────────────────────────────────────
    // getInt() Tests
    // ─────────────────────────────────────────

    @Test
    void getInt_ShouldReturnCorrectInt_WhenValidInputGiven() {
        Scanner scanner = new Scanner("5\n");
        int result = InputValidator.getInt(scanner, "Enter: ");
        assertEquals(5, result);
    }

    @Test
    void getInt_ShouldReturnZero_WhenInputIsZero() {
        Scanner scanner = new Scanner("0\n");
        int result = InputValidator.getInt(scanner, "Enter: ");
        assertEquals(0, result);
    }

    @Test
    void getInt_ShouldReturnNegativeInt_WhenNegativeInputGiven() {
        Scanner scanner = new Scanner("-10\n");
        int result = InputValidator.getInt(scanner, "Enter: ");
        assertEquals(-10, result);
    }

    @Test
    void getInt_ShouldReturnLargeInt_WhenLargeInputGiven() {
        Scanner scanner = new Scanner("999999\n");
        int result = InputValidator.getInt(scanner, "Enter: ");
        assertEquals(999999, result);
    }

    @Test
    void getInt_ShouldSkipInvalidInput_AndReturnValidInt() {
        // First input "abc" is invalid, second "42" is valid
        Scanner scanner = new Scanner("abc\n42\n");
        int result = InputValidator.getInt(scanner, "Enter: ");
        assertEquals(42, result);
    }

    @Test
    void getInt_ShouldSkipMultipleInvalidInputs_AndReturnValidInt() {
        // Multiple invalid inputs before valid one
        Scanner scanner = new Scanner("abc\nxyz\n!\n10\n");
        int result = InputValidator.getInt(scanner, "Enter: ");
        assertEquals(10, result);
    }

    // ─────────────────────────────────────────
    // getString() Tests
    // ─────────────────────────────────────────

    @Test
    void getString_ShouldReturnCorrectString_WhenValidInputGiven() {
        Scanner scanner = new Scanner("Hello World\n");
        String result = InputValidator.getString(scanner, "Enter: ");
        assertEquals("Hello World", result);
    }

    @Test
    void getString_ShouldReturnEmptyString_WhenEmptyInputGiven() {
        Scanner scanner = new Scanner("\n");
        String result = InputValidator.getString(scanner, "Enter: ");
        assertEquals("", result);
    }

    @Test
    void getString_ShouldReturnString_WithNumbers() {
        Scanner scanner = new Scanner("12345\n");
        String result = InputValidator.getString(scanner, "Enter: ");
        assertEquals("12345", result);
    }

    @Test
    void getString_ShouldReturnString_WithSpecialCharacters() {
        Scanner scanner = new Scanner("abc@123!\n");
        String result = InputValidator.getString(scanner, "Enter: ");
        assertEquals("abc@123!", result);
    }

    @Test
    void getString_ShouldReturnString_WithSpaces() {
        Scanner scanner = new Scanner("John Doe\n");
        String result = InputValidator.getString(scanner, "Enter: ");
        assertEquals("John Doe", result);
    }

    @Test
    void getString_ShouldReturnNotNull() {
        Scanner scanner = new Scanner("test\n");
        String result = InputValidator.getString(scanner, "Enter: ");
        assertNotNull(result);
    }
}
