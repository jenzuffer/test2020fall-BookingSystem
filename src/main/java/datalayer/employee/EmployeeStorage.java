package datalayer.employee;

import dto.Employee;

import java.util.Collection;
import java.util.Date;

public interface EmployeeStorage {
    public int createEmployee(String firstName, String lastName, Date birthdate, String job_description);
    public Collection<Employee> getEmployeeWithID(int employeeID);
}
