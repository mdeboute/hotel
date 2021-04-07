package hotelproject.controllers;

public class User {
    private String username;
    private String password;
    private boolean admin;
    private boolean reception_staff;


    public User() {
    }

    public User(String username, String password, boolean admin, boolean reception_staff) {
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.reception_staff = reception_staff;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isReception_staff() {
        return reception_staff;
    }

    public void setReception_staff(boolean reception_staff) {
        this.reception_staff = reception_staff;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                ", reception_staff=" + reception_staff +
                '}';
    }
}
