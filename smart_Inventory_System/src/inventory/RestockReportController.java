package inventory;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class RestockReportController {

    @FXML private TableView<Product> restockTable;
    @FXML private TableColumn<Product, Integer> idColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, Integer> quantityColumn;
    @FXML private TableColumn<Product, Integer> thresholdColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        thresholdColumn.setCellValueFactory(new PropertyValueFactory<>("threshold"));

        loadLowStockProducts();
    }

    private void loadLowStockProducts() {
        ObservableList<Product> lowStockList = FXCollections.observableArrayList();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM products WHERE quantity < threshold";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                lowStockList.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getInt("threshold")
                ));
            }

            restockTable.setItems(lowStockList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
