package hotel.management.system;

import java.sql.*;

public class conn {
    public Connection c;
    public Statement s;

    public conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3300/hotelmanagementsystem", "root", "12345" // Use your password here
            );
            s = c.createStatement();
        } catch (Exception e) {
            System.out.println("Database connection error: " + e);
        }
    }
}