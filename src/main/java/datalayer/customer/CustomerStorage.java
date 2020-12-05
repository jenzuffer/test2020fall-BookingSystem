package datalayer.customer;

import dto.Customer;
import dto.CustomerCreation;

import java.sql.SQLException;
import java.util.List;

public interface CustomerStorage {
    public Customer getCustomerWithId(int customerId);

    public List<Customer> getCustomersByFirstname(String firstname) throws SQLException;

    public List<Customer> getAllCustomers() throws SQLException;

    public int createCustomer(CustomerCreation customerToCreate) throws SQLException;

    public String updateCustomerWithPhoneNumber(Customer customer, String phoneNumber) throws SQLException;
}
