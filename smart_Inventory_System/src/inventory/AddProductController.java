package inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddProductController {

    @FXML private TextField nameField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField thresholdField;
    @FXML private Button saveButton;

  
    private DashboardController dashboardController;

    // Setter for dependency injection
    public void setDashboardController(DashboardController controller) {
        this.dashboardController = controller;
    }

    @FXML
    private void handleAddProduct() {
        String name = nameField.getText();
        int quantity;
        double price;
        int threshold;

        try {
            quantity = Integer.parseInt(quantityField.getText());
            price = Double.parseDouble(priceField.getText());
            threshold = Integer.parseInt(thresholdField.getText());

            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO products (name, quantity, price, threshold) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, price);
            stmt.setInt(4, threshold);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Product added successfully!");
                
             
                if (dashboardController != null) {
                    dashboardController.refreshStats();
                }

                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add product.");
            }

            conn.close();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid numbers.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        nameField.clear();
        quantityField.clear();
        priceField.clear();
        thresholdField.clear();
    }
}
