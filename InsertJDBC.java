import java.sql.*;
import java.util.Scanner;

public class InsertJDBC {
    static int SId;
    static String SName;
    static String SCity;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/student";
            String username = "root";
            String password = "";

            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();

            // Create the table if it does not exist
            try {
                String createTableQuery = "CREATE TABLE IF NOT EXISTS student (sid INT(20) PRIMARY KEY, sName VARCHAR(30) NOT NULL, sCity VARCHAR(200))";
                stmt.executeUpdate(createTableQuery);
                // System.out.println("Table created successfully...");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Insert data into the table
            String insertQuery = "INSERT INTO student(sid, sName, sCity) VALUES (?, ?, ?)";
            try {
                input(scanner);
                pstmt = con.prepareStatement(insertQuery);
                pstmt.setInt(1, SId);
                pstmt.setString(2, SName);
                pstmt.setString(3, SCity);
                int status = pstmt.executeUpdate();
                if (status > 0) {
                    System.out.println("Record is inserted successfully !!!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Select data from table
            String selectQuery = "SELECT * FROM student";
            try(ResultSet rs = pstmt.executeQuery(selectQuery)){
                
                while (rs.next()) {
                    int sid = rs.getInt("sid");
                    String sName = rs.getString("sName");
                    String sCity = rs.getString("sCity");

                    // Print or process the retrieved data
                    System.out.println("Student ID: " + sid + ", Name: " + sName + ", City: " + sCity);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Close resources
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void input(Scanner scanner) {
        System.out.println("Enter the student id:");
        SId = scanner.nextInt();
        System.out.println("Enter the Student Name:");
        SName = scanner.next();
        System.out.println("Enter the City:");
        SCity = scanner.next();
    }
}
