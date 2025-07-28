package inventory;

import java.sql.Connection;

public class TestBDB {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Connected to MySQL successfully!");
        } else {
            System.out.println("❌ Failed to connect.");
        }
    }
}

