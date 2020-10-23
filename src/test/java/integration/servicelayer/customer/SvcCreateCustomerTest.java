package integration.servicelayer.customer;

import datalayer.customer.CustomerStorageImpl;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
class SvcCreateCustomerTest {

    private CustomerService svc;

    private static final String PASSWORD = "testuser123";


    @BeforeAll
    public void setup() {
        String url = "jdbc:mysql://0.0.0.0:3307/";
        String db = "DemoApplicationTest";
        Flyway flyway = new Flyway(
                new FluentConfiguration()
                        .schemas(db)
                        .defaultSchema(db)
                        .createSchemas(true)
                        .target("3")
                        .dataSource(url, "root", PASSWORD)
        );
        flyway.migrate();
        svc = new CustomerServiceImpl(new CustomerStorageImpl(url + db, "root", PASSWORD));
    }

    @Test
    public void mustSaveCustomerToDatabaseWhenCallingCreateCustomer() throws CustomerServiceException, SQLException {
        // Arrange
        var firstName = "John";
        var lastName = "Johnson";
        var bday = new Date(1239821l);
        String phonenumber = "2391053";
        int id = svc.createCustomer(firstName, lastName, bday, phonenumber);

        // Act
        var createdCustomer = svc.getCustomerById(id);

        // Assert
        assertEquals(firstName, createdCustomer.getFirstname());
        assertEquals(lastName, createdCustomer.getLastname());
    }
}
