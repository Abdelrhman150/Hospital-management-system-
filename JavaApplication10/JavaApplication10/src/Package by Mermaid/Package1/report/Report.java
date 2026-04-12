package Package1.report;

/**
 * The Abstraction in the Bridge Pattern.
 * This class holds a reference to the Implementor (ReportFormatter).
 */
public abstract class Report {
    protected ReportFormatter formatter;

    protected Report() {
    }

    protected Report(ReportFormatter formatter) {
        this.formatter = formatter;
    }

 
   
    protected abstract void generate();


    public String getFullContent() {
 
        this.generate();
        
 
        return formatter.getFormattedReport();
    }

    public void exportToFile(String fileName) {
        this.generate();
        formatter.saveToFile(fileName);
    }
}
