package java_reference;

public class Authenticate {
    private int loginAttempts;

    public Authenticate() {
        this.loginAttempts = 0;
    }

    public boolean login(User user, String username, String password) {
        if (user == null || username == null || password == null) {
            System.out.println("Invalid login details.");
            return false;
        }

        if (user.getEmail().equals(username) && user.validateCredentials(password)) {
            System.out.println("Login successful for: " + user.getName());
            loginAttempts = 0;
            return true;
        }

        loginAttempts++;
        System.out.println("Login failed. Attempt " + loginAttempts);

        if (loginAttempts >= 3) {
            System.out.println("Account locked due to too many failed attempts.");
        }

        return false;
    }

    public void logout(User user) {
        if (user != null) {
            user.logout();
            System.out.println("User " + user.getName() + " has been logged out.");
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void resetLoginAttempts() {
        loginAttempts = 0;
    }

    @Override
    public String toString() {
        return "Authenticate{" +
                "loginAttempts=" + loginAttempts +
                '}';
    }
}