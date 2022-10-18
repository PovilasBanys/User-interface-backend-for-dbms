package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DbOperations {


    public static Connection connectToDb() {

        String DB_URL = "jdbc:sqlserver://PC\\SQLEXPRESS:1433;databaseName=db teisinga su data;encrypt=true;trustServerCertificate=true;AuthenticationMethod=type2";
        String USER = "povilasdb";
        String PASSWORD = "povilas";
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Couldn't get database connection.");
            e.printStackTrace();
        }
        return connection;
    }


//    public static Connection connectToDb() {
//        Connection connection = null;
//
//        try {
//
//            String dbURL = "jdbc:sqlserver://PC\\SQLEXPRESS";
//            String user = "povilasdb";
//            String pass = "povilas";
//            connection = DriverManager.getConnection(dbURL, user, pass);
//
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return connection;
//    }

//
//    public static Connection connectToDb() {
//        String HOST = "PC";
//        int PORT = 1433;
//        String DB_NAME = "db teisinga su data";
//        String USERNAME = "povilasdb";
//        String PASSWORD = "povilas";
//        Connection connection = null;
//
//
//            try {
//                connection = DriverManager.getConnection(String.format("jdbc:mssql://%s:%d/%s", HOST, PORT, DB_NAME), USERNAME, PASSWORD);
//            } catch (SQLException ex) {
//                Logger.getLogger(DbOperations.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            return connection;
//        }
//





//    public static void disconnectFromDb(Connection connection, Statement statement) {
//        try {
//            if (connection != null && statement != null) {
//                connection.close();
//                statement.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    
}

