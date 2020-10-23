package dto;

import java.sql.Time;
import java.util.Date;

public class Booking {
    private final int id;
    private final int customerid;
    private final int employeeid;
    private final Date date;
    private final Time start;
    private final Time end;


    public int getCustomerid() {
        return customerid;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public Date getDate() {
        return date;
    }

    public Booking(int id, int customerid, int employeeid, Date date, Time start, Time end) {
        this.id = id;
        this.customerid = customerid;
        this.employeeid = employeeid;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }
}
