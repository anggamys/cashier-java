package models;

public class Owner extends Person{

    private String username;
    private String password;

    public Owner(String id, String name, String email, String phoneNumber, String address, String username, String password) {
        super(id, name, email, phoneNumber, address);
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}