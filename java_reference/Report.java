package java_reference;
import java.util.Date;

public class Report {
    private String reportType;
    private Date startDate;
    private Date endDate;
    private Date generatedDate;

    public Report(String reportType, Date startDate, Date endDate) {
        this.reportType = reportType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.generatedDate = new Date();
    }

    public void generateReport() {
        this.generatedDate = new Date();
        System.out.println("Generating " + reportType +
                " report from " + startDate + " to " + endDate +
                " on " + generatedDate);
    }

    public void exportReport(String format) {
        System.out.println("Exporting " + reportType +
                " report in " + format + " format.");
    }

    public String getReportType() {
        return reportType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportType='" + reportType + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", generatedDate=" + generatedDate +
                '}';
    }
}