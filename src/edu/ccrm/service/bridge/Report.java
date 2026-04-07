package edu.ccrm.service.bridge;

/**
 * Defines WHAT to report. Delegates HOW to render to the ReportRenderer implementor.
 * Abstraction and implementation can vary independently.
 */
public abstract class Report {
    protected final ReportRenderer renderer;

    public Report(ReportRenderer renderer) {
        this.renderer = renderer;
    }

    public abstract void generate();
}
