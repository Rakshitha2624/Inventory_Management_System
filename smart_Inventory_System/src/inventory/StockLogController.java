package inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class StockLogController {

    @FXML private TextField productIdField;
    @FXML private ChoiceBox<String> actionChoiceBox;
    @FXML private TextField quantityField;

    @FXML
    public void initialize() {
        actionChoiceBox.getItems().addAll("IN", "OUT");
    }
    @FXML
    private void handleStockUpdate() {
        try {
            int productId = Integer.parseInt(productIdField.getText().trim());
            String action = actionChoiceBox.getValue();
            int quantity = Integer.parseInt(quantityField.getText().trim());

            if (action == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select an action (IN/OUT).");
                return;
            }

            Connection conn = DBConnection.getConnection();

            // 🔍 Check if product ID exists
            String checkProductSQL = "SELECT * FROM products WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkProductSQL);
            checkStmt.setInt(1, productId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Product", "No product found with ID: " + productId);
                conn.close();
                return;
            }

            // ✅ Proceed to insert stock log
            String sql = "INSERT INTO stock_logs (product_id, action_type, quantity) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, productId);
            stmt.setString(2, action);
            stmt.setInt(3, quantity);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Stock movement recorded successfully.");
                productIdField.clear();
                quantityField.clear();
                actionChoiceBox.setValue(null);
            } else {
                showAlert(Alert.AlertType.ERROR, "Failure", "Failed to record stock movement.");
            }

            conn.close();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numeric values.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while recording stock movement.");
        }
    }


    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
