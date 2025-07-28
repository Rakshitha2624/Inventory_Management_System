package inventory;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;


public class DashboardController {

	@FXML private Label totalProductsLabel;
	@FXML private Label lowStockLabel;
	@FXML private HBox chartArea;
    @FXML
    private BorderPane dashboardPane;

    @FXML
    public void initialize() {
        refreshStats();      // Update total products and low stock
        loadCharts();        // Add this to load charts into chartArea
    }

    private void loadCharts() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();

            // ----- Bar Chart: Stock by Product -----
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Product");
            yAxis.setLabel("Quantity");

            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Product Stock Levels");

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Stock");

            ResultSet rs1 = stmt.executeQuery("SELECT name, quantity FROM products");
            while (rs1.next()) {
                series.getData().add(new XYChart.Data<>(rs1.getString("name"), rs1.getInt("quantity")));
            }
            barChart.getData().add(series);
            barChart.setPrefHeight(300);
            barChart.setLegendVisible(false);

            // ----- Pie Chart: In Stock vs Low Stock -----
            PieChart pieChart = new PieChart();
            pieChart.setTitle("Stock Status");

            ResultSet rs2 = stmt.executeQuery("SELECT " +
                    "(SELECT COUNT(*) FROM products WHERE quantity >= threshold) AS inStock, " +
                    "(SELECT COUNT(*) FROM products WHERE quantity < threshold) AS lowStock");

            if (rs2.next()) {
                pieChart.getData().add(new PieChart.Data("In Stock", rs2.getInt("inStock")));
                pieChart.getData().add(new PieChart.Data("Low Stock", rs2.getInt("lowStock")));
            }

            pieChart.setPrefHeight(300);

            // ----- Add both charts to chartArea -----
         // Clear and create a VBox for vertical stacking of charts
            chartArea.getChildren().clear();
            VBox vbox = new VBox(30); // 30px spacing between charts
            vbox.setAlignment(Pos.CENTER); // center-align inside VBox

            // Styling for bar chart
            barChart.setPrefHeight(250);
            barChart.setPrefWidth(500);
            barChart.setStyle("-fx-padding: 10;");
            barChart.setLegendVisible(false);

            // Styling for pie chart
            pieChart.setPrefHeight(250);
            pieChart.setPrefWidth(350);
            pieChart.setStyle("-fx-padding: 10;");

            // Add both charts to VBox
            vbox.getChildren().addAll(barChart, pieChart);

            // Optionally, make VBox grow inside HBox
            HBox.setHgrow(vbox, Priority.ALWAYS);

            // Add VBox to the HBox chartArea
            chartArea.getChildren().add(vbox);


            chartArea.getChildren().clear();
            chartArea.getChildren().add(vbox);


            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @FXML
    private void handleViewProducts(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewProducts.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("View All Products");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleBackup() {
        DatabaseUtils.backupDatabase((Stage) dashboardPane.getScene().getWindow());
    }

    @FXML
    private void handleRestore() {
        DatabaseUtils.restoreDatabase((Stage) dashboardPane.getScene().getWindow());
    }

    
    @FXML
    private void handleAddProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
            Parent root = loader.load();

            // Inject DashboardController into AddProductController
            AddProductController controller = loader.getController();
            controller.setDashboardController(this);

            Stage stage = new Stage();
            stage.setTitle("Add Product");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void refreshStats() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rs1 = stmt.executeQuery("SELECT COUNT(*) AS total FROM products");
            if (rs1.next()) {
                totalProductsLabel.setText(String.valueOf(rs1.getInt("total")));
            }

            ResultSet rs2 = stmt.executeQuery("SELECT COUNT(*) AS low FROM products WHERE quantity < threshold");
            if (rs2.next()) {
                lowStockLabel.setText(String.valueOf(rs2.getInt("low")));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleVendors(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Vendors.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Vendors");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLowStock(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LowStockReport.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Low Stock Report");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) totalProductsLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login - Smart Inventory");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleRestockReport() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RestockReport.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Restock Report");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @FXML
    private void handleViewVendors(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewVendors.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Vendor List");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleStockLog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StockLog.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Stock Movement");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @FXML
    private void handleViewStockLogs(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewStockLog.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Stock Movement Logs");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showDashboard() {
        initialize();
    }
}
