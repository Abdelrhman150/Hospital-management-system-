package Package2;

/**
 * Concrete Implementor: PDF format.
 * This class handles the specific logic for PDF formatting.
 */
public class PDFFormatter implements ReportFormatter {
    private StringBuilder report = new StringBuilder();

    @Override
    public void formatHeader(String title) {
        // شرح الحل: إعادة تهيئة StringBuilder لضمان عدم تراكم محتوى التقارير السابقة
        report.setLength(0); 
        report.append("[PDF Header]: ").append(title.toUpperCase()).append("\n");
        report.append("--------------------------------------------------\n");
    }

    @Override
    public void formatBody(String content) {
        report.append("[PDF Body]:\n").append(content).append("\n");
    }

    @Override
    public void formatFooter(String footer) {
        report.append("--------------------------------------------------\n");
        report.append("[PDF Footer]: ").append(footer).append("\n");
    }

    @Override
    public String getFormattedReport() {
        return report.toString();
    }
}
