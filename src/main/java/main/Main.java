package main;

import datalayer.launching.CreateTables;
import dto.Customer;
import datalayer.customer.CustomerStorageImpl;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import java.sql.SQLException;

public class Main {
    //my docker instance runs on 0.0.0.0:3307
    private static final String conStr = "jdbc:mysql://0.0.0.0:3307/";
    private static final String user = "root";
    private static final String pass = "testuser123";

    public static void setup() {
        String url = conStr;
        String db = "DemoApplication";
        Flyway flyway = new Flyway(
                new FluentConfiguration()
                        .schemas(db)
                        .defaultSchema(db)
                        .createSchemas(true)
                        .target("3")
                        .dataSource(url, user, pass)
        );
        flyway.migrate();
    }

    public static void main(String[] args) throws SQLException {
        setup();

        //new CreateTables(conStr, user, pass);
        CustomerStorageImpl storage = new CustomerStorageImpl(conStr, user, pass);
        System.out.println("Got customers: ");
        for(Customer c : storage.getAllCustomers()) {
            System.out.println(toString(c));
        }
        System.out.println("The end.");
    }

    public static String toString(Customer c) {
        return "{" + c.getId() + ", " + c.getFirstname() + ", " + c.getLastname() + "}";
    }
}
