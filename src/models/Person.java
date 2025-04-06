package models;

public abstract class Person {

    protected String id;
    protected String name;
    protected String email;
    protected int phoneNumber;
    protected String address;

    public Person(String id, String name, String email, int phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getId() {return id;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public int getPhoneNumber() {return phoneNumber;}
    public String getAddress() {return address;}
}