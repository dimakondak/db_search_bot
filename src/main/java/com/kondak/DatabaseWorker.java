package com.kondak;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseWorker {

    DatabaseConnectionInfo info;
    Connection connection;
    Statement connectionStatement;

    static int expectedRowCount = 1;
    static String prefix = "[DatabaseWorker]: ";


    private void log(String text) {
        System.out.println(prefix + text);
    }

    public DatabaseWorker(DatabaseConnectionInfo info) {
        log("Database connection info retrieved!");
        this.info = info;
    }

    public Boolean isConnected() {
        return connection != null;
    }

    //method that returns true if the connection to the database is successful and false if it is unsuccessful
    public Boolean connect() {
        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://" + info.getAddress() + ":" + info.getPort() + "/" + info.getDatabaseName(),
                            info.getUsername(),
                            info.getPassword());
            connectionStatement = connection.createStatement();

        } catch (SQLException e) {
            log("Database connection failed");
            log(e.getMessage());
            return false;
        }
        log("Successfully connected to database!");
        return true;
    }

    //the method receives three string values and creates a new user in the database
    //returns true on success and false on failure
    public Boolean add(String name, String surname, String Phone) {
        Boolean result = false;

        String query = "INSERT INTO `Data`(`Name`, `Surname`, `Phone`) VALUES ('" + name + "','" + surname + "','" + Phone + "')";
        log("Query to exec: " + query);

        try {
            if (connectionStatement.executeUpdate(query) == expectedRowCount) //a single line append query has been sent, so we expect to see one line added
                result = true;
        } catch (Exception e) {
            log("Query execution fail!");
            log(e.getMessage());
        }

        return result;
    }

    //the method requests a search in the database by keyword
    //returns a String with the result
    public String search(String pattern) {
        String result = "";

        if (!isConnected()) return "<No database connection>"; //this line is needed for the method to work ALWAYS

        String query = "SELECT * FROM Data WHERE Name LIKE '%" + pattern + "%' OR Surname LIKE '%" + pattern + "%' OR Phone LIKE '%" + pattern + "%'";
        log("Query to exec: " + query);

        try {
            ResultSet rs = connectionStatement.executeQuery(query);
            while (rs.next()) {
                //column numbering in MySQL starts from 1, 1st is 'id'
                result += rs.getString(2) + " " +
                        rs.getString(3) + " " +
                        rs.getString(4) + "\n";
            }
        } catch (Exception e) {
            log("Query execution fail!");
            log(e.getMessage());
        }

        return result;
    }
}