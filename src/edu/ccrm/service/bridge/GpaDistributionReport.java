package edu.ccrm.service.bridge;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.ccrm.domain.Student;
import edu.ccrm.service.proxy.DataStoreInterface;
import edu.ccrm.util.GpaCalculator;

/**
 * Defines WHAT data the GPA report contains. Delegates rendering to ReportRenderer.
 */
public class GpaDistributionReport extends Report {
    private final DataStoreInterface dataStore;

    public GpaDistributionReport(ReportRenderer renderer, DataStoreInterface dataStore) {
        super(renderer);
        this.dataStore = dataStore;
    }

    @Override
    public void generate() {
        List<Student> students = dataStore.getAllStudents();

        Map<String, Long> gpaRanges = students.stream()
                .collect(Collectors.groupingBy(this::categorizeGpa, Collectors.counting()));

        renderer.renderHeader("GPA Distribution Report");
        gpaRanges.forEach((range, count) -> renderer.renderDataRow(range, count));
        renderer.renderFooter();
    }

    private String categorizeGpa(Student student) {
        double gpa = GpaCalculator.calculate(student);
        if (gpa >= 3.5) return "Excellent (3.5-4.0)";
        if (gpa >= 3.0) return "Good (3.0-3.49)";
        if (gpa >= 2.5) return "Average (2.5-2.99)";
        return "Below Average (<2.5)";
    }
}
