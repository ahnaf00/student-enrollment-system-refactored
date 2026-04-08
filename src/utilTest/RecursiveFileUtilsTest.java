package utilTest;

import edu.ccrm.util.RecursiveFileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class RecursiveFileUtilsTest {

    @TempDir
    Path tempDir;

    // ─────────────────────────────────────────
    // calculateDirectorySize() Tests
    // ─────────────────────────────────────────

    @Test
    void calculateDirectorySize_ShouldReturnZero_WhenDirectoryIsEmpty() {
        long size = RecursiveFileUtils.calculateDirectorySize(tempDir);
        assertEquals(0, size);
    }

    @Test
    void calculateDirectorySize_ShouldReturnCorrectSize_WhenOneFileExists()
            throws IOException {
        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, "Hello"); // 5 bytes
        long size = RecursiveFileUtils.calculateDirectorySize(tempDir);
        assertEquals(5, size);
    }

    @Test
    void calculateDirectorySize_ShouldReturnCorrectSize_WhenMultipleFilesExist()
            throws IOException {
        Files.writeString(tempDir.resolve("file1.txt"), "Hello");   // 5 bytes
        Files.writeString(tempDir.resolve("file2.txt"), "World!!");  // 7 bytes
        long size = RecursiveFileUtils.calculateDirectorySize(tempDir);
        assertEquals(12, size);
    }

    @Test
    void calculateDirectorySize_ShouldReturnCorrectSize_WhenSubDirectoryExists()
            throws IOException {
        // Create subdirectory with a file
        Path subDir = tempDir.resolve("subdir");
        Files.createDirectory(subDir);
        Files.writeString(subDir.resolve("file.txt"), "SubFile"); // 7 bytes
        Files.writeString(tempDir.resolve("root.txt"), "Root");   // 4 bytes

        long size = RecursiveFileUtils.calculateDirectorySize(tempDir);
        assertEquals(11, size);
    }

    @Test
    void calculateDirectorySize_ShouldReturnPositiveValue_WhenFileExists()
            throws IOException {
        Files.writeString(tempDir.resolve("test.txt"), "Some content");
        long size = RecursiveFileUtils.calculateDirectorySize(tempDir);
        assertTrue(size > 0);
    }

    @Test
    void calculateDirectorySize_ShouldReturnZero_WhenOnlySubDirExistsWithNoFiles()
            throws IOException {
        Path subDir = tempDir.resolve("emptySubDir");
        Files.createDirectory(subDir);
        long size = RecursiveFileUtils.calculateDirectorySize(tempDir);
        assertEquals(0, size);
    }

    @Test
    void calculateDirectorySize_ShouldHandleNestedSubDirectories()
            throws IOException {
        // nested: tempDir/sub1/sub2/file.txt
        Path sub1 = tempDir.resolve("sub1");
        Path sub2 = sub1.resolve("sub2");
        Files.createDirectories(sub2);
        Files.writeString(sub2.resolve("deep.txt"), "DeepFile"); // 8 bytes

        long size = RecursiveFileUtils.calculateDirectorySize(tempDir);
        assertEquals(8, size);
    }

    @Test
    void calculateDirectorySize_ShouldReturnLong_NotNegative()
            throws IOException {
        Files.writeString(tempDir.resolve("test.txt"), "content");
        long size = RecursiveFileUtils.calculateDirectorySize(tempDir);
        assertTrue(size >= 0);
    }
}