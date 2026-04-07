package edu.ccrm.service.bridge;


public class ConsoleReportRenderer implements ReportRenderer {

    @Override
    public void renderHeader(String title) {
        System.out.println("\n--- " + title + " ---");
    }

    @Override
    public void renderDataRow(String label, long value) {
        System.out.printf("%s: %d students\n", label, value);
    }

    @Override
    public void renderFooter() {
        System.out.println("--- End of Report ---");
    }
}
