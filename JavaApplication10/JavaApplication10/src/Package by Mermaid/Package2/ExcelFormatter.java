package Package2;

/**
 * Concrete Implementor: Excel format.
 * This class handles the specific logic for Excel formatting.
 */
public class ExcelFormatter implements ReportFormatter {
    private StringBuilder report = new StringBuilder();

    @Override
    public void formatHeader(String title) {
        // شرح الحل: تصفير StringBuilder قبل البدء لضمان نظافة التقرير الجديد ومنع تراكم البيانات
        report.setLength(0);
        report.append("[Excel Title Sheet]: ").append(title).append("\n");
        report.append("==================================================\n");
    }

    @Override
    public void formatBody(String content) {
        report.append("[Excel Rows & Columns Data]:\n").append(content.replace("\n", " | ")).append("\n");
    }

    @Override
    public void formatFooter(String footer) {
        report.append("==================================================\n");
        report.append("[Excel Summary Sheet]: ").append(footer).append("\n");
    }

    @Override
    public String getFormattedReport() {
        return report.toString();
    }
}
