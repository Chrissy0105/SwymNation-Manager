package java_reference;

public class Instructor extends User {
    private int instructorId;
    private String specialty;
    private ClassSchedule classSchedule;
    
    public Instructor(int userId, String name, String email, String password, String role,
            int instructorId, String specialty) {
        super(userId, name, email, password, role);
        this.instructorId = instructorId;
        this.specialty = specialty;
        this.classSchedule = new ClassSchedule(instructorId);
    }

    public void createClassSchedule() {
        this.classSchedule = new ClassSchedule(instructorId);
        System.out.println("Class schedule created for instructor: " + getName());
    }

    public void updateClassSchedule(ClassSession session) {
        if (session != null) {
            classSchedule.addSession(session);
            System.out.println("Session added for instructor: " + getName());
        }
    }

    public void displayClassSchedule() {
        System.out.println("Schedule for instructor: " + getName());
        classSchedule.displaySchedule();
    }

    public void manageClientProgress() {
        System.out.println("Managing client progress for instructor: " + getName() +
                " (ID: " + instructorId + ")");
    }

    public void sendNotification(String message) {
        System.out.println("Notification from " + getName() + ": " + message);
    }

    public void markAttendance(Client client, ClassSession session, String status) {
        if (client == null || session == null || status == null || status.trim().isEmpty()) {
            System.out.println("Invalid attendance details.");
            return;
        }

        System.out.println("Instructor " + getName() +
                " marked attendance for client " + client.getName() +
                " in session ID " + session.getSessionId() +
                " as: " + status);
    }

    public void trackPerformanceMetrics(int clientId, String metrics) {
        System.out.println("Tracking performance metrics for client ID: " + clientId +
                " by instructor: " + getName() + ". Metrics: " + metrics);
    }

    public void trackAttendance(int clientId, String date) {
        System.out.println("Tracking attendance for client ID: " + clientId +
                " on date: " + date + " by instructor: " + getName());
    }

    public void provideFeedback(int clientId, String feedback) {
        System.out.println("Providing feedback for client ID: " + clientId +
                " by instructor: " + getName() + ". Feedback: " + feedback);
    }

    public void manageClientProgress(int clientId, String progressUpdate) {
        System.out.println("Updating progress for client ID: " + clientId +
                " by instructor: " + getName() + ". Progress update: " + progressUpdate);
    }

    public void updateProfile(String name, String email, String specialty) {
        super.updateProfile(name, email);
        this.specialty = specialty;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public String getSpecialty() {
        return specialty;
    }

    public ClassSchedule getClassSchedule() {
        return classSchedule;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "instructorId=" + instructorId +
                ", specialty='" + specialty + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role='" + getRole() + '\'' +
                ", classSchedule=" + classSchedule +
                '}';
    }
}