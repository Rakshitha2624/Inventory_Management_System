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

public class ViewStockLogController {

    @FXML private TableView<StockLog> stockLogTable;
    @FXML private TableColumn<StockLog, Integer> idColumn;
    @FXML private TableColumn<StockLog, Integer> productIdColumn;
    @FXML private TableColumn<StockLog, String> actionColumn;
    @FXML private TableColumn<StockLog, Integer> quantityColumn;
    @FXML private TableColumn<StockLog, String> timestampColumn;

    private ObservableList<StockLog> stockLogs = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        loadStockLogs();
    }

    private void loadStockLogs() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM stock_logs");

            while (rs.next()) {
                StockLog log = new StockLog(
                    rs.getInt("id"),
                    rs.getInt("product_id"),
                    rs.getString("action_type"),
                    rs.getInt("quantity"),
                    rs.getString("timestamp")
                );
                stockLogs.add(log);
            }

            stockLogTable.setItems(stockLogs);

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
