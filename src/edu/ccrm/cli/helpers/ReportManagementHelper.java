package edu.ccrm.cli.helpers;

import java.util.Scanner;

import edu.ccrm.service.ReportService;
import edu.ccrm.util.InputValidator;

public class ReportManagementHelper {
    private final ReportService reportService;
    
    public ReportManagementHelper(ReportService reportService) {
        this.reportService = reportService;
    }
    
    public void handleReports(Scanner scanner) {
        System.out.println("\n--- Reports ---");
        System.out.println("1. GPA Distribution Report");
        
        int choice = InputValidator.getInt(scanner, "Enter choice: ");
        
        if (choice == 1) {
            reportService.generateGpaDistributionReport();
        } else {
            System.out.println("Invalid choice.");
        }
    }
}