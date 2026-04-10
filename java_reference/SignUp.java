package java_reference;

public class SignUp {
    private String name;
    private String email;
    private String password;
    private String role;
    
    public SignUp(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role != null ? role.toLowerCase() : null;
    }

    public boolean validateDetails() {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return false;
        }

        if (email == null || !email.contains("@")) {
            System.out.println("Invalid email address.");
            return false;
        }

        if (password == null || password.length() < 6) {
            System.out.println("Password must be at least 6 characters long.");
            return false;
        }

        if (role == null || role.trim().isEmpty()) {
            System.out.println("Role cannot be empty.");
            return false;
        }

        return true;
    }

    public Client createClient(int userId, int clientId) {
        if (!validateDetails() || !role.equals("client")) {
            System.out.println("Invalid client details.");
            return null;
        }

        return new Client(userId, name, email, password, role, clientId, "active");
    }

    public Instructor createInstructor(int userId, int instructorId, String specialty) {
        if (!validateDetails() || !role.equals("instructor")) {
            System.out.println("Invalid instructor details.");
            return null;
        }

        return new Instructor(userId, name, email, password, role, instructorId, specialty);
    }

    public Receptionist createReceptionist(int userId, int employeeId) {
        if (!validateDetails() || !role.equals("receptionist")) {
            System.out.println("Invalid receptionist details.");
            return null;
        }

        return new Receptionist(userId, name, email, password, role, employeeId);
    }

    public void displaySummary() {
        System.out.println("Sign-Up Summary:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Role: " + role);
    }

    @Override
    public String toString() {
        return "SignUp{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}