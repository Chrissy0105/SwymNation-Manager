package java_reference;

public class StudentProgress {
    private int studentId;
    private String performanceLevel;
    private String comments;

    public StudentProgress(int studentId, String performanceLevel, String comments) {
        this.studentId = studentId;
        this.performanceLevel = performanceLevel;
        this.comments = comments;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getPerformanceLevel() {
        return performanceLevel;
    }

    public String getComments() {
        return comments;
    }

    public void updateProgress(String performanceLevel, String comments) {
        if (performanceLevel != null && !performanceLevel.trim().isEmpty()) {
            this.performanceLevel = performanceLevel;
        }

        if (comments != null && !comments.trim().isEmpty()) {
            this.comments = comments;
        }
    }

    // ✅ Useful helper method
    public String getProgressDetails() {
        return "Student ID: " + studentId +
                ", Performance Level: " + performanceLevel +
                ", Comments: " + comments;
    }

    @Override
    public String toString() {
        return "StudentProgress{" +
                "studentId=" + studentId +
                ", performanceLevel='" + performanceLevel + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}