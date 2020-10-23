package servicelayer.booking;

import datalayer.booking.BookingStorage;
import dto.Booking;

import java.util.Collection;
import java.util.Date;

public class BookingServiceImp implements BookingService {
    private BookingStorage bookingStorage;
    public BookingServiceImp(BookingStorage bookingStorage){
        this.bookingStorage = bookingStorage;
    }

    @Override
    public int createBooking(int customerID, int employeeID, Date start, Date end) {
        return bookingStorage.createBooking(customerID, employeeID, start, end);
    }

    @Override
    public Collection<Booking> getBookingsForCustomers(int customerID) {
        return bookingStorage.getBookingsForCustomers(customerID);
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeID) {
        return bookingStorage.getBookingsForEmployees(employeeID);
    }
}
