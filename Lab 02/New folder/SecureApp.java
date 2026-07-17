import java.sql.*;
import java.util.Scanner;

/**
 * EC7020 Lab 2 - Task (e)
 * SECURED application: uses a PreparedStatement with a bound parameter.
 * User input is sent separately from the SQL, so it is treated as DATA,
 * never as executable SQL. The injection strings now match no user.
 */
public class SecureApp {

    static final String DB_URL  = "jdbc:mysql://localhost:3306/lab2_security";
    static final String DB_USER = "root";
    static final String DB_PASS = "your_mysql_password";   // <-- CHANGE THIS

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username to search: ");
        String username = sc.nextLine();

        // ---- THE FIX: parameterised query, input bound as a value ----
        String sql = "SELECT * FROM UserTable WHERE username = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);   // input is escaped/parameterised automatically
            System.out.println("\n[DEBUG] Prepared SQL: " + sql + "  [param = " + username + "]\n");

            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
