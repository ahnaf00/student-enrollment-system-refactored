package edu.ccrm.service;

import edu.ccrm.service.bridge.GpaDistributionReport;
import edu.ccrm.service.bridge.ReportRenderer;
import edu.ccrm.service.proxy.DataStoreInterface;

/**
 * ReportRenderer (implementor) is injected — can be console, file, HTML, etc.
 */
public class ReportService {
    private final DataStoreInterface dataStore;
    private final ReportRenderer renderer;
    
    public ReportService(DataStoreInterface dataStore, ReportRenderer renderer) {
        this.dataStore = dataStore;
        this.renderer = renderer;
    }
    
    public void generateGpaDistributionReport() {
        GpaDistributionReport report = new GpaDistributionReport(renderer, dataStore);
        report.generate();
    }

}