package Package1.report;

/**
 * The Abstraction in the Bridge Pattern AND the Template in the Template Method Pattern.
 * - Bridge: Holds a reference to the Implementor (ReportFormatter).
 * - Template Method: Defines the fixed algorithm structure (generateReport).
 */
public abstract class Report {
    protected ReportFormatter formatter;

    protected Report() {
    }

    protected Report(ReportFormatter formatter) {
        this.formatter = formatter;
    }

    
    public final void generateReport() {
        drawHeader();
        insertMainData();
        addDetails();
        finalSignature();
    }

    
    protected abstract void drawHeader();
    protected abstract void insertMainData();
    protected abstract void addDetails();
    protected abstract void finalSignature();

    
    public String getFullContent() {
        this.generateReport();
        return formatter.getFormattedReport();
    }
}
