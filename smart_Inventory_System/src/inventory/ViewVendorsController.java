package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;

public class ViewVendorsController {

    @FXML private TableView<Vendor> vendorTable;
    @FXML private TableColumn<Vendor, Number> idColumn;
    @FXML private TableColumn<Vendor, String> nameColumn;
    @FXML private TableColumn<Vendor, String> contactColumn;
    @FXML private TableColumn<Vendor, String> emailColumn;
    @FXML private TableColumn<Vendor, String> addressColumn;

    private ObservableList<Vendor> vendorList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> data.getValue().idProperty());
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        contactColumn.setCellValueFactory(data -> data.getValue().contactProperty());
        emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());
        addressColumn.setCellValueFactory(data -> data.getValue().addressProperty());

        loadVendors();
    }

    private void loadVendors() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM vendors";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            vendorList.clear();
            while (rs.next()) {
                vendorList.add(new Vendor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("contact"),
                    rs.getString("emailid"),
                    rs.getString("address")
                ));
            }
            vendorTable.setItems(vendorList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
