package hotelproject.controllers;

public class User {
    private String u_name;
    private String u_password;
    private int u_is_admin;

    public User() {
    }

    public User(String u_name, String u_password, int u_is_admin) {
        this.u_name = u_name;
        this.u_password = u_password;
        this.u_is_admin = u_is_admin;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public int getU_is_admin() {
        return u_is_admin;
    }

    public void setU_is_admin(int u_is_admin) {
        this.u_is_admin = u_is_admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "u_name='" + u_name + '\'' +
                ", u_password='" + u_password + '\'' +
                ", u_is_admin=" + u_is_admin +
                '}';
    }
}
