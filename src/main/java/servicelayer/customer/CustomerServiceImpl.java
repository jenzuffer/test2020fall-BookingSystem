package servicelayer.customer;

import datalayer.customer.CustomerStorage;
import dto.Customer;
import dto.CustomerCreation;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public class CustomerServiceImpl implements CustomerService {

    private CustomerStorage customerStorage;

    public CustomerServiceImpl(CustomerStorage customerStorage) {
        this.customerStorage = customerStorage;
    }

    @Override
    public int createCustomer(String firstName, String lastName, Date birthdate, String phoneNumber) throws CustomerServiceException {
        try {
            return customerStorage.createCustomer(new CustomerCreation(phoneNumber, firstName, lastName, birthdate));
        } catch (SQLException throwables) {
            throw new CustomerServiceException(throwables.getMessage());
        }
    }

    @Override
    public Customer getCustomerById(int id) {
        return customerStorage.getCustomerWithId(id);
    }

    @Override
    public Collection<Customer> getCustomersByFirstName(String firstName) {
        Collection<Customer> customers = null;
        if (firstName == null || firstName.isEmpty())
            return customers;
        try {
            for (Customer customer : customerStorage.getCustomersByFirstname(firstName)) {
                    customers.add(customer);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return customers;
        }
    }
}
