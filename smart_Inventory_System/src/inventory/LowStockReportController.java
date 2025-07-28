package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LowStockReportController {

    @FXML private TableView<Product> tableView;
    @FXML private TableColumn<Product, Integer> colId;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, Integer> colQuantity;
    @FXML private TableColumn<Product, Integer> colThreshold;

    private ObservableList<Product> lowStockList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colThreshold.setCellValueFactory(new PropertyValueFactory<>("threshold"));

        loadLowStock();
    }

    private void loadLowStock() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products WHERE quantity < threshold")) {

            while (rs.next()) {
                lowStockList.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"), // You can ignore price column here
                        rs.getInt("threshold")
                ));
            }
            tableView.setItems(lowStockList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
