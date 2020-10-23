package datalayer.employee;

import dto.Employee;

import java.sql.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public class EmployeeStorageImpl implements EmployeeStorage {

    private String connectionString;
    private String username, password;

    public EmployeeStorageImpl(String conStr, String user, String pass) {
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public int createEmployee(String firstName, String lastName, Date birthdate, String job_description) {
        String sql = "insert into Customers(firstname, lastname, birthdate, job_description) values (?, ?, ?, ?)";
        int anInt = 0;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setDate(3, new java.sql.Date(birthdate.getTime()));
            ps.setString(4, job_description);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            anInt = generatedKeys.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return anInt;
        }
    }

    @Override
    public Collection<Employee> getEmployeeWithID(int employeeID) {
        Collection<Employee> employeeCollection = Arrays.asList();
        String sql = "SELECT * FROM DemoApplication.Employees WHERE ID = (?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, employeeID);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                employeeCollection.add(new Employee(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getDate(5)));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeeCollection;
    }
}
