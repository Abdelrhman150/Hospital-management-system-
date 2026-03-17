package Package2;

/**
 * Concrete Implementor: PDF format.
 * This class handles the specific logic for PDF formatting.
 */
public class PDFFormatter implements ReportFormatter {
    private StringBuilder report = new StringBuilder();

    @Override
    public void formatHeader(String title) {
       
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

    @Override
    public void saveToFile(String fileName) {
        // Since we don't have a binary PDF library, we'll save as HTML which opens in browsers
        // and can be "Printed to PDF" easily.
        String htmlFileName = fileName.replace(".pdf", ".html");
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.File(htmlFileName))) {
            writer.println("<html><head><style>");
            writer.println("body { font-family: sans-serif; padding: 40px; }");
            writer.println(".header { color: #2e75b4; border-bottom: 2px solid #2e75b4; padding-bottom: 10px; }");
            writer.println(".content { margin-top: 20px; line-height: 1.6; }");
            writer.println(".footer { margin-top: 40px; font-style: italic; color: gray; border-top: 1px solid #ddd; }");
            writer.println("</style></head><body>");
            
            String[] parts = report.toString().split("--------------------------------------------------");
            if (parts.length >= 3) {
                writer.println("<h1 class='header'>" + parts[0].replace("[PDF Header]: ", "") + "</h1>");
                writer.println("<div class='content'><pre>" + parts[1].replace("[PDF Body]:", "").trim() + "</pre></div>");
                writer.println("<div class='footer'>" + parts[2].replace("[PDF Footer]: ", "") + "</div>");
            }
            
            writer.println("</body></html>");
            System.out.println("Report saved as HTML (Open and Print to PDF): " + htmlFileName);
            
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(new java.io.File(htmlFileName));
            }
        } catch (Exception e) {
            System.err.println("Error saving HTML: " + e.getMessage());
        }
    }
}
