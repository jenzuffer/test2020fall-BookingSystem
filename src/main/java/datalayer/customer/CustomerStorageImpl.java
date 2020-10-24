package datalayer.customer;

import dto.Customer;
import dto.CustomerCreation;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

public class CustomerStorageImpl implements CustomerStorage {
    private String connectionString;
    private String username, password;

    public CustomerStorageImpl(String conStr, String user, String pass) {
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public Customer getCustomerWithId(int customerId) {
        String sql = "select ID, firstname, lastname, birthdate, phonenumber from Customers where id = ?";
        try (Connection con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (var resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    var id = resultSet.getInt("ID");
                    var firstname = resultSet.getString("firstname");
                    var lastname = resultSet.getString("lastname");
                    Date birthdate = resultSet.getDate("birthdate");
                    String phonenumber = resultSet.getString("phonenumber");
                    return new Customer(id, phonenumber, firstname, lastname, birthdate);
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Customer> getCustomers() throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {
            Collection<Customer> results = new LinkedHashSet();

            ResultSet resultSet = stmt.executeQuery("select ID, firstname, lastname, birthdate, phonenumber from Customers");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                Date birthdate = resultSet.getDate("birthdate");
                String phonenumber = resultSet.getString("phonenumber");
                Customer c = new Customer(id, phonenumber, firstname, lastname, birthdate);
                results.add(c);
            }
            List<Customer> customerlist = new ArrayList();
            customerlist.addAll(results);
            return customerlist;
        }
    }

    public int createCustomer(CustomerCreation customerToCreate) throws SQLException {
        String sql = "insert into Customers(firstname, lastname, birthdate, phonenumber) values (?, ?, ?, ?)";
        try (Connection con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customerToCreate.getFirstname());
            stmt.setString(2, customerToCreate.getLastname());
            stmt.setDate(3, new java.sql.Date(customerToCreate.getBirthdate().getTime()));
            stmt.setString(4, customerToCreate.getPhoneNumber());
            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);
                System.out.println("newID: " + newId);
                return newId;
            }
        }
    }
}
