package dto;

import java.util.Date;

public class Customer {
    private final int id;
    private String phoneNumber;
    private final String firstname, lastname;
    private final Date birthdate;

    public Date getBirthdate() {
        return birthdate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Customer(int id, String firstname, String lastname, Date birthdate){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }

    public Customer(int id, String phoneNumber, String firstname, String lastname, Date birthdate) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
