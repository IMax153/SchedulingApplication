/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class which manages connecting to and disconnecting from the UCertify MySQL
 * database.
 *
 * @author mab90
 */
public class Database {

    /**
     * The connection to the UCertify MySQL database.
     */
    private static Connection connection;

    /**
     * The MySQL JDBC driver.
     */
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";

    /**
     * The UCertify MySQL connection protocol.
     */
    private static final String CONNECTION_PROTOCOL = "jdbc:mysql";

    /**
     * The UCertify MySQL database server name.
     */
    private static final String SERVER = "3.227.166.251";

    /**
     * The UCertify MySQL database database name.
     */
    private static final String DATABASE_NAME = "U05N3b";

    /**
     * The UCertify MySQL database database password.
     */
    private static final String DATABASE_PASSWORD = "53688549581";

    /**
     * Connects to the UCertify MySQL database.
     *
     * @throws ClassNotFoundException
     * @throws Exception
     */
    public static void createConnection() throws ClassNotFoundException, Exception {
        try {
            System.out.println("Connecting to the database...");

            Class.forName(MYSQL_JDBC_DRIVER);

            String url = String.format(
                    "%s://%s/%s",
                    CONNECTION_PROTOCOL,
                    SERVER,
                    DATABASE_NAME
            );

            connection = DriverManager.getConnection(url, DATABASE_NAME, DATABASE_PASSWORD);

            System.out.println("Successfully connected to the database");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Unable to find the correct class.");
            throw cnfe;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Disconnects from the UCertify MySQL database.
     *
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException {
        try {
            System.out.println("Closing the connection to the database...");

            connection.close();

            System.out.println("Successfully closed the connection to the database");
        } catch (SQLException e) {
            System.out.println(
                    "An error occurred while closing the connection "
                    + "to the UCertify MySQL database"
            );

            throw e;
        }
    }

    /**
     * Gets the connection to the UCertify MySQL database.
     *
     * @return The connection to the UCertify MySQL database.
     */
    public static Connection getConnection() {
        return connection;
    }
}
