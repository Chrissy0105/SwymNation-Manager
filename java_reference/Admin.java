package java_reference;
import java.util.Date;

public class Admin extends User {
    private int adminId;

    public Admin(int userId, String name, String email, String password, String role, int adminId) {
        super(userId, name, email, password, role);
        this.adminId = adminId;
    }

    public void manageUsers() {
        System.out.println("Managing users for admin: " + getName());
    }

    public void manageClasses() {
        System.out.println("Managing classes for admin: " + getName());
    }

    public Report generateReport(String reportType, Date startDate, Date endDate) {
        System.out.println("Generating " + reportType + " report from " + startDate + " to " + endDate);
        return new Report(reportType, startDate, endDate);
    }

    @Override
    public void logout() {
        super.logout();
        System.out.println("Admin-specific logout actions completed for: " + getName());
    }

    public int getAdminId() {
        return adminId;
    }
    
    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", userId=" + getUserId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role='" + getRole() + '\'' +
                '}';
    }
}