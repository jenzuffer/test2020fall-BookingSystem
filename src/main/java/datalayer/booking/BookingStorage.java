package datalayer.booking;

import dto.Booking;

import java.util.Collection;
import java.util.Date;

public interface BookingStorage {
    public int createBooking(int customerID, int employeeID, Date start, Date end);
    public Collection<Booking> getBookingsForCustomers(int customerID);

    public Collection<Booking> getBookingsForEmployees(int employeeID);
}
