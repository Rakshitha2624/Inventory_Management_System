package inventory;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleRegister() {
        // Registration logic
        String user = usernameField.getText();
        String pass = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            errorLabel.setText("All fields are required.");
        } else if (!pass.equals(confirm)) {
            errorLabel.setText("Passwords do not match.");
        } else {
            errorLabel.setText("Registered Successfully!");
            // Save to DB if needed
        }
    }

    @FXML
    private void handleCancel() {
        // Close the registration window
        usernameField.getScene().getWindow().hide();
    }
}
