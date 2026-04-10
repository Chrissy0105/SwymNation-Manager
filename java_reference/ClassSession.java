package java_reference;
import java.util.Date;

public class ClassSession {
    private int sessionId;
    private Date date;
    private String time;
    private String level;
    private int capacity;
    private int currentEnrollment;

    public ClassSession(int sessionId, Date date, String time, String level, int capacity) {
        this.sessionId = sessionId;
        this.date = date;
        this.time = time;
        this.level = level;
        this.capacity = capacity;
        this.currentEnrollment = 0;
    }

    public int getSessionId() {
        return sessionId;
    }

    public Date getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLevel() {
        return level;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentEnrollment() {
        return currentEnrollment;
    }

    public boolean checkAvailability() {
        return currentEnrollment < capacity;
    }

    public boolean enrollStudent() {
        if (checkAvailability()) {
            currentEnrollment++;
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (currentEnrollment > 0) {
            currentEnrollment--;
            return true;
        }
        return false;
    }

    public String getSessionDetails() {
        return "Session ID: " + sessionId +
                ", Date: " + date +
                ", Time: " + time +
                ", Level: " + level +
                ", Capacity: " + capacity +
                ", Current Enrollment: " + currentEnrollment;
    }

    @Override
    public String toString() {
        return "ClassSession{" +
                "sessionId=" + sessionId +
                ", date=" + date +
                ", time='" + time + '\'' +
                ", level='" + level + '\'' +
                ", capacity=" + capacity +
                ", currentEnrollment=" + currentEnrollment +
                '}';
    }
}