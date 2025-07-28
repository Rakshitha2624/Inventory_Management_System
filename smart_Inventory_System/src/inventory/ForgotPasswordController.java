package inventory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ForgotPasswordController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleResetPassword() {
        String username = usernameField.getText();
        String newPassword = newPasswordField.getText();

        if (username.isEmpty() || newPassword.isEmpty()) {
            statusLabel.setText("Fields cannot be empty");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Check if user exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // User found, update password
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
                updateStmt.setString(1, newPassword);
                updateStmt.setString(2, username);
                int rowsAffected = updateStmt.executeUpdate();

                if (rowsAffected > 0) {
                    statusLabel.setText("Password reset successful!");
                } else {
                    statusLabel.setText("Error updating password");
                }
            } else {
                statusLabel.setText("User not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Database error");
        }
    }
}
