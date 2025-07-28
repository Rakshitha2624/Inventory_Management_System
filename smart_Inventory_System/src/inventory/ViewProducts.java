package inventory;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class ViewProducts extends Application {

    private TableView<Product> table = new TableView<>();
    private ObservableList<Product> data = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        // Define columns
        TableColumn<Product, String> nameCol = new TableColumn<>("Product Name");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Product, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        TableColumn<Product, Integer> thresholdCol = new TableColumn<>("Threshold");
        thresholdCol.setCellValueFactory(cellData -> cellData.getValue().thresholdProperty().asObject());

        table.getColumns().addAll(nameCol, quantityCol, priceCol, thresholdCol);

        loadDataFromDatabase(); // call to fetch from DB

        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Inventory - View Products");
        stage.show();
    }

    private void loadDataFromDatabase() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM products";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
            	data.add(new Product(
            		    rs.getInt("id"),
            		    rs.getString("name"),
            		    rs.getInt("quantity"),
            		    rs.getDouble("price"),
            		    rs.getInt("threshold")
            		));

            }

            table.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
