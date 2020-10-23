package dto;

import java.util.Date;

public class CustomerCreation {
    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public Date getBirthdate() {
        return this.birthdate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public final String phoneNumber;

    public final Date birthdate;

    public final String firstname, lastname;

    public CustomerCreation(String phoneNumber, String firstname, String lastname, Date birthdate) {
        this.phoneNumber = phoneNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
    }
}
