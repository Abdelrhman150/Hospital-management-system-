package Package2;

/**
 * The Implementor Interface in the Bridge Pattern.
 * This defines how a report should be formatted (PDF, Excel, etc.)
 */
public interface ReportFormatter {
    void formatHeader(String title);
    void formatBody(String content);
    void formatFooter(String footer);
    String getFormattedReport();
    void saveToFile(String fileName); // Added for real file generation
}
