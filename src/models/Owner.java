package models;

public class Owner extends Person{

    private String username;
    private String password;

    public Owner(String id, String name, String email, int phoneNumber, String address, String username, String password) {
        super(id, name, email, phoneNumber, address);
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}