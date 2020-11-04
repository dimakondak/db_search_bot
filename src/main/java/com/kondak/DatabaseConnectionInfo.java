package com.kondak;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConnectionInfo {

    private static final Logger log = LogManager.getLogger();
    private String address;
    private String port;
    private String username;
    private String password;
    private String databaseName;

    public void setAddress(String address) {
        this.address = address;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getAddress() {
        return address;
    }
    public String getPort() {
        return port;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getDatabaseName() {
        return databaseName;
    }

    public DatabaseConnectionInfo() {
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);

            this.address = property.getProperty("db.address");
            this.port = property.getProperty("db.port");
            this.username = property.getProperty("db.username");
            this.password = property.getProperty("db.password");
            this.databaseName = property.getProperty("db.name");

        } catch (IOException e) {
            log.error("Properties file is missing!");
        }
    }

    public DatabaseConnectionInfo(String address, String port, String username, String password, String databaseName) {
        this.address = address;
        this.port = port;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
    }
}
