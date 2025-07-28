package inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class VendorController {

    @FXML private TextField nameField;
    @FXML private TextField contactField;
    @FXML private TextField emailField;
    @FXML private TextArea addressField;
    @FXML private Label messageLabel;

    @FXML
    private void handleAddVendor() {
        String name = nameField.getText();
        String contact = contactField.getText();
        String emailid = emailField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || contact.isEmpty() || emailid.isEmpty() || address.isEmpty()) {
            messageLabel.setText("⚠️ Please fill all fields.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO vendors (name, contact, emailid, address) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, contact);
            stmt.setString(3, emailid);
            stmt.setString(4, address);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                messageLabel.setText("✅ Vendor added successfully.");
                clearFields();
            } else {
                messageLabel.setText("❌ Failed to add vendor.");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("❌ Error: " + e.getMessage());
        }
    }

    private void clearFields() {
        nameField.clear();
        contactField.clear();
        emailField.clear();
        addressField.clear();
    }
}
