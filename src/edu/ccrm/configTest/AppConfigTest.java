package edu.ccrm.configTest;

import edu.ccrm.config.AppConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppConfigTest {

    // ─────────────────────────────────────────
    // Singleton Pattern Tests
    // ─────────────────────────────────────────

    @Test
    void getInstance_ShouldNotReturnNull() {
        assertNotNull(AppConfig.getInstance());
    }

    @Test
    void getInstance_ShouldReturnSameInstance_WhenCalledTwice() {
        AppConfig instance1 = AppConfig.getInstance();
        AppConfig instance2 = AppConfig.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void getInstance_ShouldReturnSameInstance_WhenCalledMultipleTimes() {
        AppConfig instance1 = AppConfig.getInstance();
        AppConfig instance2 = AppConfig.getInstance();
        AppConfig instance3 = AppConfig.getInstance();
        assertSame(instance1, instance2);
        assertSame(instance2, instance3);
        assertSame(instance1, instance3);
    }

    // ─────────────────────────────────────────
    // getDataDirectory() Tests
    // ─────────────────────────────────────────

    @Test
    void getDataDirectory_ShouldNotReturnNull() {
        assertNotNull(AppConfig.getInstance().getDataDirectory());
    }

    @Test
    void getDataDirectory_ShouldNotBeEmpty() {
        assertFalse(AppConfig.getInstance().getDataDirectory().isEmpty());
    }

    @Test
    void getDataDirectory_ShouldReturnCorrectValue() {
        assertEquals("data", AppConfig.getInstance().getDataDirectory());
    }

    // ─────────────────────────────────────────
    // getBackupDirectory() Tests
    // ─────────────────────────────────────────

    @Test
    void getBackupDirectory_ShouldNotReturnNull() {
        assertNotNull(AppConfig.getInstance().getBackupDirectory());
    }

    @Test
    void getBackupDirectory_ShouldNotBeEmpty() {
        assertFalse(AppConfig.getInstance().getBackupDirectory().isEmpty());
    }

    @Test
    void getBackupDirectory_ShouldReturnCorrectValue() {
        assertEquals("backup", AppConfig.getInstance().getBackupDirectory());
    }

    // ─────────────────────────────────────────
    // getMaxCreditsPerSemester() Tests
    // ─────────────────────────────────────────

    @Test
    void getMaxCreditsPerSemester_ShouldReturnCorrectValue() {
        assertEquals(18, AppConfig.getInstance().getMaxCreditsPerSemester());
    }

    @Test
    void getMaxCreditsPerSemester_ShouldReturnPositiveValue() {
        assertTrue(AppConfig.getInstance().getMaxCreditsPerSemester() > 0);
    }

    @Test
    void getMaxCreditsPerSemester_ShouldBeConsistent_WhenCalledMultipleTimes() {
        int first  = AppConfig.getInstance().getMaxCreditsPerSemester();
        int second = AppConfig.getInstance().getMaxCreditsPerSemester();
        assertEquals(first, second);
    }

    // ─────────────────────────────────────────
    // Immutability Tests
    // ─────────────────────────────────────────

    @Test
    void dataDirectory_ShouldAlwaysReturnSameValue() {
        String first  = AppConfig.getInstance().getDataDirectory();
        String second = AppConfig.getInstance().getDataDirectory();
        assertEquals(first, second);
    }

    @Test
    void backupDirectory_ShouldAlwaysReturnSameValue() {
        String first  = AppConfig.getInstance().getBackupDirectory();
        String second = AppConfig.getInstance().getBackupDirectory();
        assertEquals(first, second);
    }
}
