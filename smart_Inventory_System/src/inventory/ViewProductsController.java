package inventory;

import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewProductsController {

    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, Integer> colId;
    @FXML
    private TableColumn<Product, String> colName;
    @FXML
    private TableColumn<Product, Integer> colQuantity;
    @FXML
    private TableColumn<Product, Double> colPrice;
    @FXML
    private TableColumn<Product, Integer> colThreshold;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colThreshold.setCellValueFactory(new PropertyValueFactory<>("threshold"));

        loadProducts();

        // Highlight low stock rows in light red
        tableView.setRowFactory(tv -> new TableRow<Product>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (product == null || empty) {
                    setStyle("");
                } else if (product.getQuantity() < product.getThreshold()) {
                    setStyle("-fx-background-color: #fce4e4;"); // Light red
                } else {
                    setStyle("");
                }
            }
        });
    }


    private void loadProducts() {
        boolean hasLowStock = false;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            productList.clear();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                int threshold = rs.getInt("threshold");

                if (quantity < threshold) {
                    hasLowStock = true;
                }

                productList.add(new Product(id, name, quantity, price, threshold));
            }

            tableView.setItems(productList);

            // Show alert if any product is low
            if (hasLowStock) {
                showLowStockAlert();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showLowStockAlert() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Low Stock Alert");
        alert.setHeaderText(null);
        alert.setContentText("⚠️ One or more products are below the threshold!");
        alert.showAndWait();
    }


    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM products WHERE id = ?";
                var stmt = conn.prepareStatement(sql);
                stmt.setInt(1, selectedProduct.getId());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    tableView.getItems().remove(selectedProduct);
                    System.out.println("✅ Product deleted successfully.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("⚠️ Please select a product to delete.");
        }
    }

}
