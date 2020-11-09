package integration.datalayer.customer;

import com.github.javafaker.Faker;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import dto.Customer;
import dto.CustomerCreation;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Testcontainers
class CreateCustomerTest {
    private CustomerStorage customerStorage;
    private Faker faker = new Faker();

    private static final String PASSWORD = "testuser123";
    private static final String USER = "root";
    private static final int PORT = 3306;


    @Container
    public static MySQLContainer mysql = (MySQLContainer) new MySQLContainer(DockerImageName.parse("mysql"))
            .withPassword(PASSWORD)
            .withExposedPorts(PORT);



    @BeforeAll
    public void Setup() throws SQLException {
        String url = "jdbc:mysql://"+mysql.getHost()+":"+mysql.getFirstMappedPort()+"/";
        var db = "DemoApplicationTest";

        Flyway flyway = new Flyway(new FluentConfiguration()
                .defaultSchema(db)
                .createSchemas(true)
                .schemas(db)
                .target("2")
                .dataSource(url, USER, PASSWORD));

        flyway.migrate();

        customerStorage = new CustomerStorageImpl(url + db, USER, PASSWORD);

        int numCustomers = customerStorage.getAllCustomers().size();
        addFakeCustomers(numCustomers);
        var firstName = "aCustomer";
        var lastName = "bCustomer";
        var birthdate = new Date(123456789l);
        String phonenumber =  "2944033";
        CustomerCreation customercreation = new CustomerCreation(firstName, lastName, birthdate);
        customerStorage.createCustomer(customercreation);

    }

    private void addFakeCustomers(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            CustomerCreation c = new CustomerCreation(faker.phoneNumber().phoneNumber(), faker.name().firstName(), faker.name().lastName(), faker.date().birthday());
            customerStorage.createCustomer(c);
        }

    }

    @Test
    public void mustSaveCustomerInDatabaseWhenCallingCreateCustomer() throws SQLException {
        // Arrange
        // Act
        java.sql.Date date1 = new java.sql.Date(new Date(1239821l).getTime());
        customerStorage.createCustomer(new CustomerCreation("7462848", "John", "Carlssonn", date1));

        // Assert
        var customers = customerStorage.getCustomersByFirstname("John");
        assertTrue(
                customers.stream().anyMatch(x ->
                        x.getFirstname().equals("John") &&
                                x.getLastname().equals("Carlssonn")));
    }

    @Test
    public void mustReturnLatestId() throws SQLException {
        // Arrange
        // Act
        java.sql.Date date1 = new java.sql.Date(new Date(1299821l).getTime());
        var id1 = customerStorage.createCustomer(new CustomerCreation( "3475934", "a", "b", date1));
        date1 = new java.sql.Date(new Date(1599821l).getTime());
        var id2 = customerStorage.createCustomer(new CustomerCreation( "4523539", "c", "d", date1));

        // Assert
        assertEquals(1, id2 - id1);
    }


    @Test
    @Disabled("this test is not ready yet")
    public void MustUpdateCustomerWithPhoneNumber() throws SQLException {
        //arrange
        List<Customer> allCustomers = customerStorage.getAllCustomers();
        String phonenumber = faker.phoneNumber().phoneNumber();
        //act
        Customer customer = allCustomers.get(0);
        String returnedPhone = customerStorage.updateCustomerWithPhoneNumber(customer, phonenumber);

        //assert
        Assertions.assertEquals(phonenumber, returnedPhone);
    }
}
