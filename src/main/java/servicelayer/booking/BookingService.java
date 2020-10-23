package servicelayer.booking;

import dto.Booking;

import java.util.Collection;
import java.util.Date;

public interface BookingService {
    public int createBooking(int customerID, int employeeID, Date start, Date end);
    public Collection<Booking> getBookingsForCustomers(int customerID);
    public Collection<Booking> getBookingsForEmployee(int employeeID);
}
