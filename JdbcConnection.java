import  java.sql.*;
import java.util.Scanner;

public class JdbcConnection {

    static String dbname;
    static String tblname;
    public static void main(String[] args) {
        try{
        Scanner scanner = new Scanner(System.in);
       
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "";

        
        Connection connection = null;
        Statement statement = null;
         connection = DriverManager.getConnection(url, username, password);

         createDatabase(connection, scanner);

        System.out.println("Enter column names with data type");
         scanner.nextLine();
         String columnDetails = scanner.nextLine().trim();

         // Ensure the selected database
         String useDatabaseQuery = "USE " + dbname;
         statement = connection.createStatement();
         statement.executeUpdate(useDatabaseQuery);

        String createTableQuery = "CREATE TABLE " + tblname + " (" + columnDetails + ")";
        statement.executeUpdate(createTableQuery);
        System.out.println("Table name " + tblname + " "  + "created in database " + dbname + "." );

        

        statement.close();
        connection.close();
        scanner.close();
    }
        
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createDatabase(Connection connection, Scanner scanner) throws SQLException{
        System.out.println("Enter Database name");
        dbname = scanner.next();
        String checkDatabaseQuery =  "CREATE DATABASE IF NOT EXISTS " +dbname;
        Statement statement = connection.createStatement();
        statement.executeUpdate(checkDatabaseQuery);
        statement.close();
        System.out.println("Enter table name");
        tblname = scanner.next();

        

    }
}

