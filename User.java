public class User {
    private String username;
    private String password;
    private boolean loggedIn;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.loggedIn = false;
    }

    public boolean checkPassword(String pw) {
        if (pw == null) return false;
        return this.password.equals(pw);
    }

    public void login() {
        this.loggedIn = true;
    }

    public void logout() {
        this.loggedIn = false;
    }

    // Getters
    public String getUsername() { return username; }
    public boolean isLoggedIn() { return loggedIn; }
}
