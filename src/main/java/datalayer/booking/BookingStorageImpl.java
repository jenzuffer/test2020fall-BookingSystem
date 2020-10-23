package datalayer.booking;

import dto.Booking;

import java.sql.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class BookingStorageImpl implements BookingStorage {

    private String connectionString;
    private String username, password;

    public BookingStorageImpl(String conStr, String user, String pass) {
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public int createBooking(int customerID, int employeeID, Date start, Date end) {
        String sql = "insert into DemoApplication.Bookings(customerId, employeeId, date, start, end) values (?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, customerID);
            stmt.setInt(2, employeeID);
            stmt.setDate(3, new java.sql.Date(new Date().getTime()));
            stmt.setTime(4, (Time) start);
            stmt.setTime(5, (Time) end);
            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);
                System.out.println("newID: " + newId);
                return newId;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return 0;
    }

    @Override
    public Collection<Booking> getBookingsForCustomers(int customerID) {
        Collection<Booking> bookings = new LinkedHashSet();
        String sql = "select ID, customerId, employeeId, `date`, `start`, `end` FROM DemoApplication.Bookings WHERE customerId = ?";
        try (Connection con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, customerID);
            try (var resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int customerid = resultSet.getInt("customerId");
                    int employeeId = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");
                    bookings.add(new Booking(id, customerid, employeeId, date, start, end));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bookings;
    }

    @Override
    public Collection<Booking> getBookingsForEmployees(int employeeID) {
        Collection<Booking> bookings = new LinkedHashSet();
        String sql = "select ID, customerId, employeeId, `date`, `start`, `end` FROM DemoApplication.Bookings WHERE employeeId = ?";
        try (Connection con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeID);
            try (var resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int customerid = resultSet.getInt("customerId");
                    int employeeId = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");
                    bookings.add(new Booking(id, customerid, employeeId, date, start, end));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bookings;
    }
}
