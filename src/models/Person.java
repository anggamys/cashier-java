package models;

public abstract class Person {

    protected String id;
    protected String name;
    protected String email;
    protected String phoneNumber;
    protected String address;

    public Person(String id, String name, String email, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getId() {return id;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPhoneNumber() {return phoneNumber;}
    public String getAddress() {return address;}
}