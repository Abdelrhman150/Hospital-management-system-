package Package1.report;

/**
 * Concrete Implementor: Excel format.
 * This class handles the specific logic for Excel-like (CSV/Tab-separated) formatting.
 */
public class ExcelFormatter implements ReportFormatter {
    private StringBuilder report = new StringBuilder();

    @Override
    public void formatHeader(String title) {
        report.setLength(0); 
        report.append("[EXCEL SHEET]: ").append(title).append("\n");
        report.append("==================================================\n");
        report.append("COLUMN_A\t|\tCOLUMN_B\n");
        report.append("--------------------------------------------------\n");
    }

    @Override
    public void formatBody(String content) {
        // Split lines and format as "cells"
        String[] lines = content.split("\n");
        for (String line : lines) {
            if (line.contains(":")) {
                String[] parts = line.split(":", 2);
                report.append(parts[0].trim()).append("\t|\t").append(parts[1].trim()).append("\n");
            } else {
                report.append(line.trim()).append("\t|\t\n");
            }
        }
    }

    @Override
    public void formatFooter(String footer) {
        report.append("--------------------------------------------------\n");
        report.append("[EXCEL FOOTER]\t|\t").append(footer).append("\n");
        report.append("==================================================\n");
    }

    @Override
    public String getFormattedReport() {
        return report.toString();
    }


}
