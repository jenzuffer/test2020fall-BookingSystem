package dto;

import java.util.Date;

public class Employee {
    private final int id;
    private final String firstname, lastname, job_description;
    private final Date birthdate;

    public Employee(int id, String firstname, String lastname, String job_description, Date birthdate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.job_description = job_description;
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
