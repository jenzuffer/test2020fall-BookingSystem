package integration.datalayer.employee;

import com.github.javafaker.Faker;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.CustomerCreation;
import dto.Employee;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
@Testcontainers
public class CreateEmployeeTest {
    private EmployeeStorage employeeStorage;
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

        employeeStorage = new EmployeeStorageImpl(url + db, USER, PASSWORD);

        int numEmployees = 5;
        addFakeEmployees(numEmployees);

    }

    private void addFakeEmployees(int numEmployees) throws SQLException {
        for (int i = 0; i < numEmployees; i++) {
            employeeStorage.createEmployee(faker.name().firstName(), faker.name().lastName(), faker.date().birthday(), faker.job().field());
        }

    }

    @Test
    public void mustSaveEmployeeInDatabaseWhenCreatingEmployee() throws SQLException {
        // Arrange
        java.sql.Date date1 = new java.sql.Date(new Date(1239821l).getTime());
        String name = faker.name().firstName();
        String lastName = faker.name().lastName();
        Date birthday = faker.date().birthday();
        String job = faker.job().field();
        // Act
        int newestEmployeeID = employeeStorage.createEmployee(name, lastName, birthday, job);
        System.out.println("newestEmployeeID: " + newestEmployeeID);
        Collection<Employee> employeeWithID = employeeStorage.getEmployeeWithID(newestEmployeeID);
        Employee employeeByID = employeeWithID.iterator().next();


        // Assert
        Assertions.assertTrue(employeeByID.getFirstname().equals(name));
        Assertions.assertTrue(employeeByID.getId() == newestEmployeeID);
    }
}
