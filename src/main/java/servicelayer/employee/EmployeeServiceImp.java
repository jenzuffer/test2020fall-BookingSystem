package servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import dto.Employee;

import java.util.Collection;
import java.util.Date;

public class EmployeeServiceImp implements EmployeeService {
    private EmployeeStorage employeeStorage;

    public EmployeeServiceImp(EmployeeStorage employeeStorage) {
        this.employeeStorage = employeeStorage;
    }


    @Override
    public int createEmployee(String firstName, String lastName, String job_description, Date birthdate) {
        return employeeStorage.createEmployee(firstName, lastName, birthdate, job_description);
    }

    @Override
    public Employee getEmployeeByID(int employeeID) {
        Collection<Employee> employeeWithID = employeeStorage.getEmployeeWithID(employeeID);
        return employeeWithID.iterator().next();
    }
}
