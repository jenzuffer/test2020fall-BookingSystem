package servicelayer.employee;

import dto.Employee;

import java.util.Date;

public interface EmployeeService {
    public int createEmployee(String firstName, String lastName, String job_description,
                              Date Birthdate);
    public Employee getEmployeeByID(int employeeID);
}
