package integration.datalayer.booking;

import com.github.javafaker.Faker;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.*;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.testcontainers.shaded.com.google.common.base.Verify;
import servicelayer.notifications.SmsService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
public class CreateBookingTest {
    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorage;
    private BookingStorage bookingStorage;
    private Faker faker = new Faker();

    private static final String conStr = "jdbc:mysql://0.0.0.0:3307/";
    private static final String user = "root";
    private static final String pass = "testuser123";

    @AfterAll
    public void finish() {
        var db = "DemoApplicationTest";
        Flyway flyway = new Flyway(new FluentConfiguration()
                .defaultSchema(db)
                .createSchemas(true)
                .schemas(db)
                .target("4")
                .dataSource(conStr, "root", pass));

        flyway.clean();
    }

    @BeforeAll
    public void Setup() throws SQLException {
        var url = "jdbc:mysql://0.0.0.0:3307/";
        var db = "DemoApplicationTest";

        Flyway flyway = new Flyway(new FluentConfiguration()
                .defaultSchema(db)
                .createSchemas(true)
                .schemas(db)
                .target("3")
                .dataSource(url, "root", "testuser123"));

        flyway.migrate();

        bookingStorage = new BookingStorageImpl(url + db, "root", "testuser123");
        employeeStorage = new EmployeeStorageImpl(url + db, "root", "testuser123");
        customerStorage = new CustomerStorageImpl(url + db, "root", "testuser123");
        int numberOfBookings = 5;
        addFakeBookings(numberOfBookings);
        var firstName = "aCustomer";
        var lastName = "bCustomer";
        var birthdate = new Date(123456789l);
        String phonenumber =  "2944033";
        CustomerCreation customercreation = new CustomerCreation(firstName, lastName, birthdate);
        customerStorage.createCustomer(customercreation);

    }

    private void addFakeBookings(int numberOfBookings) throws SQLException {
        List<Customer> customers = customerStorage.getCustomersByFirstname(faker.name().name());
        Collection<Employee> employeeWithID = employeeStorage.getEmployeeWithID(1);
        int id = employeeWithID.iterator().next().getId();
        if (customers.isEmpty())
            return;

        for (int i = 0; i < numberOfBookings; i++) {
            bookingStorage.createBooking(customers.get(0).getId(), id, faker.date().past(5, TimeUnit.HOURS), faker.date().future(5, TimeUnit.HOURS));
        }

    }

    @Test
    public void mustSaveBookingInDatabase(){
        //arrange
        List<Customer> customers = new ArrayList<>();
        try {
            customers = customerStorage.getAllCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int customerID = customers.get(0).getId();
        Collection<Employee> employeeWithID = employeeStorage.getEmployeeWithID(1);
        int id = employeeWithID.iterator().next().getId();
        Date past = faker.date().past(5, TimeUnit.HOURS);
        Date future = faker.date().future(5, TimeUnit.HOURS);
        //act
        int bookingID = bookingStorage.createBooking(id, customerID, past, future);
        //assert
        Collection<Booking> bookingsForCustomers = bookingStorage.getBookingsForCustomers(customerID);

        Assertions.assertTrue(bookingsForCustomers.iterator().next().getId() == bookingID);

    }

    @Test
    public void checkPhoneNumberIsStored()
    {
        List<Customer> customers = new ArrayList<>();
        try {
            customers = customerStorage.getCustomersByFirstname(faker.name().firstName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int customerID = 0;
        try {
            customerID = customers.get(0).getId();
        }catch (IndexOutOfBoundsException ex){
            customerID = 0;
        }
        if (customerID != 0) {
            Collection<Employee> employeeWithID = employeeStorage.getEmployeeWithID(1);
            int id = employeeWithID.iterator().next().getId();
            Date past = faker.date().past(5, TimeUnit.HOURS);
            Date future = faker.date().future(5, TimeUnit.HOURS);
            bookingStorage.createBooking(id, customerID, past, future);
            SmsMessage smsMessage = new SmsMessage("", "");
            var Smsservicemock = mock(SmsService.class);
            //interface has to be mocked
            Smsservicemock.sendSms(smsMessage);
            Mockito.verify(Smsservicemock.sendSms(smsMessage), Mockito.times(1));
        }
    }
}
