import java.sql.*;
import java.util.Scanner;

/**
 * EC7020 Lab 2 - Task (c) & (d)
 * VULNERABLE application: builds SQL by string concatenation.
 * This lets us demonstrate SQL injection WITHOUT changing the source code,
 * simply by typing malicious input at the prompt.
 */
public class VulnerableApp {

    static final String DB_URL  = "jdbc:mysql://localhost:3306/lab2_security";
    static final String DB_USER = "root";
    static final String DB_PASS = "your_mysql_password";   // <-- CHANGE THIS

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username to search: ");
        String username = sc.nextLine();

        // ---- THE VULNERABILITY: user input concatenated straight into SQL ----
        String sql = "SELECT * FROM UserTable WHERE username = '" + username + "'";
        System.out.println("\n[DEBUG] Executing SQL: " + sql + "\n");

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int count = 0;
            while (rs.next()) {
                count++;
                System.out.printf("id=%d | username=%s | password=%s | email=%s | role=%s%n",
                        rs.getInt("id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("email"),
                        rs.getString("role"));
            }
            if (count == 0) System.out.println("No matching user found.");
            System.out.println("\nRows returned: " + count);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
