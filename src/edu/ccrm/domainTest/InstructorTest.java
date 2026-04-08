package edu.ccrm.domainTest;

import edu.ccrm.domain.Instructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InstructorTest {

    private Instructor instructor;

    @BeforeEach
    void setUp() {
        instructor = new Instructor(1, "Dr. Smith", "smith@example.com",
                LocalDate.of(1975, 3, 20), "CSE", "Room 101");
    }

    // ─────────────────────────────────────────
    // Constructor Tests
    // ─────────────────────────────────────────

    @Test
    void constructor_ShouldCreateInstructor_WhenValidInputsGiven() {
        assertNotNull(instructor);
    }

    @Test
    void constructor_ShouldSetId_Correctly() {
        assertEquals(1, instructor.getId());
    }

    @Test
    void constructor_ShouldSetFullName_Correctly() {
        assertEquals("Dr. Smith", instructor.getFullName());
    }

    @Test
    void constructor_ShouldSetEmail_Correctly() {
        assertEquals("smith@example.com", instructor.getEmail());
    }

    @Test
    void constructor_ShouldSetDateOfBirth_Correctly() {
        assertEquals(LocalDate.of(1975, 3, 20), instructor.getDateOfBirth());
    }

    @Test
    void constructor_ShouldSetDepartment_Correctly() {
        assertEquals("CSE", instructor.getDepartment());
    }

    @Test
    void constructor_ShouldSetOfficeLocation_Correctly() {
        assertEquals("Room 101", instructor.getOfficeLocation());
    }

    // ─────────────────────────────────────────
    // getProfile() Tests
    // ─────────────────────────────────────────

    @Test
    void getProfile_ShouldNotReturnNull() {
        assertNotNull(instructor.getProfile());
    }

    @Test
    void getProfile_ShouldContainFullName() {
        assertTrue(instructor.getProfile().contains("Dr. Smith"));
    }

    @Test
    void getProfile_ShouldContainDepartment() {
        assertTrue(instructor.getProfile().contains("CSE"));
    }

    @Test
    void getProfile_ShouldContainOfficeLocation() {
        assertTrue(instructor.getProfile().contains("Room 101"));
    }

    @Test
    void getProfile_ShouldContainHeader() {
        assertTrue(instructor.getProfile().contains("--- Instructor Profile ---"));
    }

    @Test
    void getProfile_ShouldNotBeEmpty() {
        assertFalse(instructor.getProfile().isEmpty());
    }

    // ─────────────────────────────────────────
    // setDepartment() / getDepartment() Tests
    // ─────────────────────────────────────────

    @Test
    void setDepartment_ShouldUpdateDepartment() {
        instructor.setDepartment("MATH");
        assertEquals("MATH", instructor.getDepartment());
    }

    @Test
    void setDepartment_ShouldUpdateDepartment_ToDifferentValue() {
        instructor.setDepartment("PHY");
        assertEquals("PHY", instructor.getDepartment());
    }

    // ─────────────────────────────────────────
    // setOfficeLocation() / getOfficeLocation() Tests
    // ─────────────────────────────────────────

    @Test
    void setOfficeLocation_ShouldUpdateOfficeLocation() {
        instructor.setOfficeLocation("Room 202");
        assertEquals("Room 202", instructor.getOfficeLocation());
    }

    @Test
    void setOfficeLocation_ShouldUpdateOfficeLocation_ToDifferentValue() {
        instructor.setOfficeLocation("Lab 5");
        assertEquals("Lab 5", instructor.getOfficeLocation());
    }

    // ─────────────────────────────────────────
    // Inherited from Person — Setter Tests
    // ─────────────────────────────────────────

    @Test
    void setFullName_ShouldUpdateFullName() {
        instructor.setFullName("Dr. Johnson");
        assertEquals("Dr. Johnson", instructor.getFullName());
    }

    @Test
    void setEmail_ShouldUpdateEmail() {
        instructor.setEmail("johnson@example.com");
        assertEquals("johnson@example.com", instructor.getEmail());
    }

    @Test
    void setDateOfBirth_ShouldUpdateDateOfBirth() {
        LocalDate newDate = LocalDate.of(1980, 6, 15);
        instructor.setDateOfBirth(newDate);
        assertEquals(newDate, instructor.getDateOfBirth());
    }

    // ─────────────────────────────────────────
    // toString() Tests (Inherited from Person)
    // ─────────────────────────────────────────

    @Test
    void toString_ShouldNotReturnNull() {
        assertNotNull(instructor.toString());
    }

    @Test
    void toString_ShouldContainId() {
        assertTrue(instructor.toString().contains("1"));
    }

    @Test
    void toString_ShouldContainFullName() {
        assertTrue(instructor.toString().contains("Dr. Smith"));
    }

    @Test
    void toString_ShouldContainEmail() {
        assertTrue(instructor.toString().contains("smith@example.com"));
    }
}
