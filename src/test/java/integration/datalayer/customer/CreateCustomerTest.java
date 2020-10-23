package integration.datalayer.customer;

import com.github.javafaker.Faker;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import dto.CustomerCreation;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateCustomerTest {
    private CustomerStorage customerStorage;

    @BeforeAll
    public void Setup() throws SQLException {
        var url = "jdbc:mysql://0.0.0.0:3307/";
        var db = "DemoApplicationTest";

        Flyway flyway = new Flyway(new FluentConfiguration()
                .defaultSchema(db)
                .createSchemas(true)
                .schemas(db)
                .target("2")
                .dataSource(url, "root", "testuser123"));

        flyway.migrate();

        customerStorage = new CustomerStorageImpl(url + db, "root", "testuser123");

        int numCustomers = customerStorage.getCustomers().size();
        addFakeCustomers(numCustomers);

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
        var customers = customerStorage.getCustomers();
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
}
