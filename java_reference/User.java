package java_reference;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String role;

    public User(int userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public boolean login(String username, String password) {
        return this.email.equals(username) && this.password.equals(password);
    }

    public void logout() {
        System.out.println(name + " has logged out.");
    }

    public void updateProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public boolean validateCredentials(String password) {
        return this.password.equals(password);
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (!this.password.equals(oldPassword)) {
            return false;
        }

        if (newPassword == null || newPassword.length() < 6) {
            return false;
        }

        this.password = newPassword;
        return true;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}