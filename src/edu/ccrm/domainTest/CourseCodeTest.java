package edu.ccrm.domainTest;

import edu.ccrm.domain.CourseCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CourseCodeTest {

    private CourseCode courseCode;

    @BeforeEach
    void setUp() {
        courseCode = new CourseCode("CSE", 101);
    }

    // ─────────────────────────────────────────
    // Constructor Tests
    // ─────────────────────────────────────────

    @Test
    void constructor_ShouldCreateCourseCode_WhenValidInputsGiven() {
        CourseCode cc = new CourseCode("CSE", 101);
        assertNotNull(cc);
    }

    @Test
    void constructor_ShouldCreateCourseCode_WithSingleCharPrefix() {
        CourseCode cc = new CourseCode("C", 1);
        assertNotNull(cc);
    }

    @Test
    void constructor_ShouldCreateCourseCode_WithLargeNumber() {
        CourseCode cc = new CourseCode("MATH", 99999);
        assertNotNull(cc);
    }

    // ─────────────────────────────────────────
    // Assertion / Invariant Tests
    // NOTE: Add -ea to VM Arguments in Run Config
    // ─────────────────────────────────────────

    @Test
    void constructor_ShouldFailAssertion_WhenPrefixIsNull() {
        assertThrows(AssertionError.class, () -> new CourseCode(null, 101));
    }

    @Test
    void constructor_ShouldFailAssertion_WhenPrefixIsEmpty() {
        assertThrows(AssertionError.class, () -> new CourseCode("", 101));
    }

    @Test
    void constructor_ShouldFailAssertion_WhenPrefixIsBlank() {
        assertThrows(AssertionError.class, () -> new CourseCode("   ", 101));
    }

    @Test
    void constructor_ShouldFailAssertion_WhenCourseNumberIsZero() {
        assertThrows(AssertionError.class, () -> new CourseCode("CSE", 0));
    }

    @Test
    void constructor_ShouldFailAssertion_WhenCourseNumberIsNegative() {
        assertThrows(AssertionError.class, () -> new CourseCode("CSE", -1));
    }

    // ─────────────────────────────────────────
    // getFullCode() Tests
    // ─────────────────────────────────────────

    @Test
    void getFullCode_ShouldReturnConcatenatedPrefixAndNumber() {
        assertEquals("CSE101", courseCode.getFullCode());
    }

    @Test
    void getFullCode_ShouldReturnCorrectCode_WithDifferentPrefix() {
        CourseCode cc = new CourseCode("MATH", 201);
        assertEquals("MATH201", cc.getFullCode());
    }

    @Test
    void getFullCode_ShouldReturnCorrectCode_WithSingleDigitNumber() {
        CourseCode cc = new CourseCode("PHY", 1);
        assertEquals("PHY1", cc.getFullCode());
    }

    @Test
    void getFullCode_ShouldNotReturnNull() {
        assertNotNull(courseCode.getFullCode());
    }

    @Test
    void getFullCode_ShouldNotReturnEmpty() {
        assertFalse(courseCode.getFullCode().isEmpty());
    }

    // ─────────────────────────────────────────
    // toString() Tests
    // ─────────────────────────────────────────

    @Test
    void toString_ShouldReturnSameAsGetFullCode() {
        assertEquals(courseCode.getFullCode(), courseCode.toString());
    }

    @Test
    void toString_ShouldReturnConcatenatedString() {
        assertEquals("CSE101", courseCode.toString());
    }

    @Test
    void toString_ShouldNotReturnNull() {
        assertNotNull(courseCode.toString());
    }

    // ─────────────────────────────────────────
    // equals() Tests
    // ─────────────────────────────────────────

    @Test
    void equals_ShouldReturnTrue_WhenSameObject() {
        assertEquals(courseCode, courseCode);
    }

    @Test
    void equals_ShouldReturnTrue_WhenSamePrefixAndNumber() {
        CourseCode cc1 = new CourseCode("CSE", 101);
        CourseCode cc2 = new CourseCode("CSE", 101);
        assertEquals(cc1, cc2);
    }

    @Test
    void equals_ShouldReturnFalse_WhenDifferentPrefix() {
        CourseCode cc1 = new CourseCode("CSE", 101);
        CourseCode cc2 = new CourseCode("MATH", 101);
        assertNotEquals(cc1, cc2);
    }

    @Test
    void equals_ShouldReturnFalse_WhenDifferentNumber() {
        CourseCode cc1 = new CourseCode("CSE", 101);
        CourseCode cc2 = new CourseCode("CSE", 202);
        assertNotEquals(cc1, cc2);
    }

    @Test
    void equals_ShouldReturnFalse_WhenComparedToNull() {
        assertNotEquals(null, courseCode);
    }

    @Test
    void equals_ShouldReturnFalse_WhenComparedToDifferentType() {
        assertNotEquals("CSE101", courseCode);
    }

    @Test
    void equals_ShouldBeSymmetric() {
        CourseCode cc1 = new CourseCode("CSE", 101);
        CourseCode cc2 = new CourseCode("CSE", 101);
        assertEquals(cc1, cc2);
        assertEquals(cc2, cc1);
    }

    @Test
    void equals_ShouldBeTransitive() {
        CourseCode cc1 = new CourseCode("CSE", 101);
        CourseCode cc2 = new CourseCode("CSE", 101);
        CourseCode cc3 = new CourseCode("CSE", 101);
        assertEquals(cc1, cc2);
        assertEquals(cc2, cc3);
        assertEquals(cc1, cc3);
    }

    // ─────────────────────────────────────────
    // hashCode() Tests
    // ─────────────────────────────────────────

    @Test
    void hashCode_ShouldBeEqual_WhenObjectsAreEqual() {
        CourseCode cc1 = new CourseCode("CSE", 101);
        CourseCode cc2 = new CourseCode("CSE", 101);
        assertEquals(cc1.hashCode(), cc2.hashCode());
    }

    @Test
    void hashCode_ShouldBeConsistent_WhenCalledMultipleTimes() {
        int hash1 = courseCode.hashCode();
        int hash2 = courseCode.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void hashCode_ShouldNotThrow() {
        assertDoesNotThrow(() -> courseCode.hashCode());
    }

    // ─────────────────────────────────────────
    // Immutability Tests
    // ─────────────────────────────────────────

    @Test
    void immutability_GetFullCode_ShouldAlwaysReturnSameValue() {
        String first  = courseCode.getFullCode();
        String second = courseCode.getFullCode();
        assertEquals(first, second);
    }

    @Test
    void immutability_ToString_ShouldAlwaysReturnSameValue() {
        String first  = courseCode.toString();
        String second = courseCode.toString();
        assertEquals(first, second);
    }

    @Test
    void immutability_HashCode_ShouldAlwaysReturnSameValue() {
        int first  = courseCode.hashCode();
        int second = courseCode.hashCode();
        assertEquals(first, second);
    }
}
