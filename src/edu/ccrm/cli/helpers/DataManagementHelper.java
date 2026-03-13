package edu.ccrm.cli.helpers;

import java.util.Scanner;

import edu.ccrm.io.BackupService;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.util.InputValidator;

public class DataManagementHelper {
    private final ImportExportService importExportService;
    private final BackupService backupService;
    
    public DataManagementHelper(ImportExportService importExportService, BackupService backupService) {
        this.importExportService = importExportService;
        this.backupService = backupService;
    }
    
    public void handleDataManagement(Scanner scanner) {
        System.out.println("\n--- Data Management ---");
        System.out.println("1. Export All Data");
        System.out.println("2. Create Backup of Exported Data");
        
        int choice = InputValidator.getInt(scanner, "Enter choice: ");
        
        switch (choice) {
            case 1 -> importExportService.exportData();
            case 2 -> backupService.createBackup();
            default -> System.out.println("Invalid choice.");
        }
    }
}