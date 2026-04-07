package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility class for common file system operations.
 * Helps to eliminate duplicated directory checking and creation code.
 */
public class FileSystemUtils {
    
    /**
     * Ensures that a directory exists by creating it and any necessary parent directories.
     * 
     * @param directory The directory path to ensure exists.
     * @throws IOException If an I/O error occurs during directory creation.
     * @throws IllegalArgumentException If the directory path is null.
     */
    public static void ensureDirectoryExists(Path directory) throws IOException {
        if (directory == null) {
            throw new IllegalArgumentException("Directory path cannot be null");
        }
        
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
    }
    
    /**
     * Safely ensures that a directory exists, suppressing any IOExceptions.
     * Useful for non-critical directory operations where logging the failure is sufficient.
     * 
     * @param directory The directory path to ensure exists.
     * @return true if the directory exists or was successfully created, false otherwise.
     */
    public static boolean ensureDirectoryExistsSafe(Path directory) {
        try {
            ensureDirectoryExists(directory);
            return true;
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Warning: Could not ensure directory exists " 
                + directory + ": " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if a given path exists and is indeed a directory.
     * 
     * @param path The path to check.
     * @return true if the path exists and is a directory, false otherwise (including null paths).
     */
    public static boolean isDirectory(Path path) {
        return path != null && Files.exists(path) && Files.isDirectory(path);
    }
}
