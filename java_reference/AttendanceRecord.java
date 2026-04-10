package java_reference;
import java.util.Date;

public class AttendanceRecord {
    private int attendanceId;
    private Date date;
    private String status;
    private Client client;
    private ClassSession session;

    public AttendanceRecord(int attendanceId, Date date, String status,
            Client client, ClassSession session) {
        this.attendanceId = attendanceId;
        this.date = date;
        this.status = (status == null || status.trim().isEmpty()) ? "Pending" : status;
        this.client = client;
        this.session = session;
    }

    public void markPresent() {
        this.status = "Present";
    }
    
    public void markAbsent() {
        this.status = "Absent";
    }

    public boolean isPresent() {
        return "Present".equalsIgnoreCase(status);
    }

    public void setStatus(String status) {
        if (status == null) {
            return;
        }

        String trimmedStatus = status.trim();

        if (trimmedStatus.equalsIgnoreCase("Present") ||
                trimmedStatus.equalsIgnoreCase("Absent") ||
                trimmedStatus.equalsIgnoreCase("Pending")) {
            this.status = trimmedStatus;
        }
    }

    public String getAttendanceDetails() {
        return "Attendance ID: " + attendanceId +
                ", Client: " + (client != null ? client.getName() : "N/A") +
                ", Session ID: " + (session != null ? session.getSessionId() : "N/A") +
                ", Date: " + date +
                ", Status: " + status;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public Client getClient() {
        return client;
    }

    public ClassSession getSession() {
        return session;
    }

    @Override
    public String toString() {
        return "AttendanceRecord{" +
                "attendanceId=" + attendanceId +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", client=" + (client != null ? client.getName() : "N/A") +
                ", sessionId=" + (session != null ? session.getSessionId() : "N/A") +
                '}';
    }
}