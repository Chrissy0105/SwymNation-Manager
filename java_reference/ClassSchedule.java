package java_reference;
import java.util.List;
import java.util.ArrayList;

public class ClassSchedule {
    private int scheduleId;
    private List<ClassSession> sessions;

    public ClassSchedule(int scheduleId) {
        this.scheduleId = scheduleId;
        this.sessions = new ArrayList<>();
    }

    public void addSession(ClassSession session) {
        if (session != null) {
            sessions.add(session);
        }
    }

    public void removeSession(int sessionId) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getSessionId() == sessionId) {
                sessions.remove(i);
                System.out.println("Session removed: ID " + sessionId);
                return;
            }
        }
        System.out.println("Session not found: ID " + sessionId);
    }

    public ClassSession findSession(int sessionId) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getSessionId() == sessionId) {
                return sessions.get(i);
            }
        }
        return null;
    }

    public void displaySchedule() {
        if (sessions.isEmpty()) {
            System.out.println("No sessions in schedule.");
            return;
        }

        System.out.println("Class Schedule ID: " + scheduleId);
        for (int i = 0; i < sessions.size(); i++) {
            System.out.println(sessions.get(i));
        }
    }

    public boolean containsSession(int sessionId) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getSessionId() == sessionId) {
                return true;
            }
        }
        return false;
    }

    public int getTotalSessions() {
        return sessions.size();
    }

    public List<ClassSession> getSchedule() {
        return sessions;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    @Override
    public String toString() {
        return "ClassSchedule{" +
                "scheduleId=" + scheduleId +
                ", totalSessions=" + sessions.size() +
                '}';
    }
}