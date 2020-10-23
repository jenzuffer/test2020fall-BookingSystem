package datalayer.launching;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateTables {
    private String connectionString;
    private String username, password;
    private static final String dbInitTables = "jdbc:mysql://0.0.0.0:3307/";

    public CreateTables(String conStr, String user, String pass) {
        connectionString = conStr;
        username = user;
        password = pass;
        createTablesIfNotExisting();
        create_default_customers();
    }

    private void create_default_customers() {
        java.sql.Date date1 = new java.sql.Date(new Date(1239821l).getTime());
        java.sql.Date date2 = new java.sql.Date(new Date(1239321l).getTime());
        java.sql.Date date3 = new java.sql.Date(new Date(1239861l).getTime());
        String sql = "INSERT into DemoApplication.Customers (firstname, lastname, birthdate, phonenumber) VALUES" +
                "(?, ?, ?, ?), (?, ?, ?, ?), (?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "john");
            stmt.setString(2, "johnsen");
            stmt.setDate(3, date1);
            stmt.setString(4,  "2393023");
            stmt.setString(5, "hans");
            stmt.setString(6, "hansen");
            stmt.setDate(7, date2);
            stmt.setString(8,  "2353023");
            stmt.setString(9, "ada");
            stmt.setString(10, "adamsen");
            stmt.setDate(11, date3);
            stmt.setString(12,  "2395023");
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Connection getConnectionInitDB() throws SQLException {
        return DriverManager.getConnection(dbInitTables, username, password);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    private void createTablesIfNotExisting() {
        String sql_create_db = "CREATE DATABASE IF NOT EXISTS DemoApplication";
        try (Connection con = getConnectionInitDB();
             PreparedStatement statement = con.prepareStatement(sql_create_db)) {
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String sql = "create table IF NOT EXISTS DemoApplication.Customers (\n" +
                "\t ID int not null auto_increment,\n" +
                "\t firstname varchar(255) not null,\n" +
                "\t lastname varchar(255) not null,\n" +
                "\t birthdate date,\n" +
                "\t phonenumber varchar(255), \n" +
                "\t PRIMARY KEY (ID)\n" +
                ")";
        String sql2 = "create table IF NOT EXISTS DemoApplication.Employees (\n" +
                "       ID int not null auto_increment,\n" +
                "       firstname varchar(255) not null,\n" +
                "       lastname varchar(255) not null,\n" +
                "       birthdate date,\n" +
                "       job_description varchar(255) not null," +
                "       PRIMARY KEY (ID)\n" +
                " )";
        String sql3 = "create table IF NOT EXISTS DemoApplication.Bookings (\t\t\n" +
                "\tID int not null auto_increment,\t\n" +
                "\tcustomerId int not null,\t\n" +
                "\temployeeId int not null,\t\n" +
                "\tdate Date not null,\t\t\n" +
                "\tstart Time not null,\t\t\n" +
                "\tend Time not null,\t\t\n" +
                "\tprimary key (ID),\t\t\n" +
                "\tforeign key (customerId)\t\n" +
                "\t\treferences Customers(ID)\n" +
                "\t\ton delete cascade,\t\n" +
                "\tforeign key (employeeId)\t\n" +
                "\t\treferences Employees(ID)\n" +
                "\t\ton delete cascade\t\n" +
                ")";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             PreparedStatement stmt2 = con.prepareStatement(sql2);
             PreparedStatement stmt3 = con.prepareStatement(sql3)) {
            stmt.execute();
            stmt2.execute();
            stmt3.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
