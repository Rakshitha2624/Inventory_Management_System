package inventory;

import javafx.beans.property.*;

public class StockLog {
    private final IntegerProperty id;
    private final IntegerProperty productId;
    private final StringProperty action;
    private final IntegerProperty quantity;
    private final StringProperty timestamp;

    public StockLog(int id, int productId, String action, int quantity, String timestamp) {
        this.id = new SimpleIntegerProperty(id);
        this.productId = new SimpleIntegerProperty(productId);
        this.action = new SimpleStringProperty(action);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.timestamp = new SimpleStringProperty(timestamp);
    }

    public IntegerProperty idProperty() { return id; }
    public IntegerProperty productIdProperty() { return productId; }
    public StringProperty actionProperty() { return action; }
    public IntegerProperty quantityProperty() { return quantity; }
    public StringProperty timestampProperty() { return timestamp; }
}
