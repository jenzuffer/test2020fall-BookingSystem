package main;

import datalayer.launching.CreateTables;
import dto.Customer;
import datalayer.customer.CustomerStorageImpl;

import java.sql.SQLException;

public class Main {
    //my docker instance runs on 0.0.0.0:3307
    private static final String conStr = "jdbc:mysql://0.0.0.0:3307/DemoApplication";
    private static final String user = "root";
    private static final String pass = "testuser123";

    public static void main(String[] args) throws SQLException {
        new CreateTables(conStr, user, pass);
        CustomerStorageImpl storage = new CustomerStorageImpl(conStr, user, pass);
        System.out.println("Got customers: ");
        for(Customer c : storage.getCustomers()) {
            System.out.println(toString(c));
        }
        System.out.println("The end.");
    }

    public static String toString(Customer c) {
        return "{" + c.getId() + ", " + c.getFirstname() + ", " + c.getLastname() + "}";
    }
}
