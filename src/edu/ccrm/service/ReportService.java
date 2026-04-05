package edu.ccrm.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.ccrm.domain.Student;
import edu.ccrm.util.GpaCalculator;

public class ReportService {
    private final DataStore dataStore;
    
    public ReportService(DataStore dataStore) {
        this.dataStore = dataStore;
    }
    
    public void generateGpaDistributionReport() {
        List<Student> students = dataStore.getAllStudents();
        
        Map<String, Long> gpaRanges = students.stream()
            .collect(Collectors.groupingBy(this::categorizeGpa, Collectors.counting()));
        
        System.out.println("\n--- GPA Distribution Report ---");
        gpaRanges.forEach((range, count) -> 
            System.out.printf("%s: %d students\n", range, count));
    }
    
    private String categorizeGpa(Student student) {
        double gpa = GpaCalculator.calculate(student);
        if (gpa >= 3.5) return "Excellent (3.5-4.0)";
        if (gpa >= 3.0) return "Good (3.0-3.49)";
        if (gpa >= 2.5) return "Average (2.5-2.99)";
        return "Below Average (<2.5)";
    }

}