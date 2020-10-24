package servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.customer.CustomerStorage;
import dto.Booking;
import dto.Customer;
import dto.SmsMessage;
import servicelayer.notifications.SmsService;

import java.util.Collection;
import java.util.Date;

public class BookingServiceImp implements BookingService {
    private SmsService smsService;
    private BookingStorage bookingStorage;
    private CustomerStorage customerStorage;
    public BookingServiceImp(BookingStorage bookingStorage){
        this.bookingStorage = bookingStorage;
    }

    @Override
    public int createBooking(int customerID, int employeeID, Date start, Date end) {
        int booking = bookingStorage.createBooking(customerID, employeeID, start, end);
        Customer customerWithId = customerStorage.getCustomerWithId(customerID);
        smsService.sendSms(new SmsMessage(customerWithId.getPhoneNumber(), "hello"));

        return booking;
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
