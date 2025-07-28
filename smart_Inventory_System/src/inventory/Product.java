package inventory;

import javafx.beans.property.*;

public class Product {
    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty quantity;
    private final DoubleProperty price;
    private final IntegerProperty threshold;

    // Constructor

    public Product(int id, String name, int quantity, int threshold) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(0.0); // default value
        this.threshold = new SimpleIntegerProperty(threshold);
    }


    // Optional second constructor (with ID, if needed)
    public Product(int id, String name, int quantity, double price, int threshold) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.threshold = new SimpleIntegerProperty(threshold);
    }

    // Getters for JavaFX properties
    public StringProperty nameProperty() { return name; }
    public IntegerProperty quantityProperty() { return quantity; }
    public DoubleProperty priceProperty() { return price; }
    public IntegerProperty thresholdProperty() { return threshold; }

    // Optional: plain getters (if needed for logic)
    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public int getQuantity() { return quantity.get(); }
    public double getPrice() { return price.get(); }
    public int getThreshold() { return threshold.get(); }

    // Optional: setters
    public void setId(int id) { this.id.set(id); }
    public void setName(String name) { this.name.set(name); }
    public void setQuantity(int quantity) { this.quantity.set(quantity); }
    public void setPrice(double price) { this.price.set(price); }
    public void setThreshold(int threshold) { this.threshold.set(threshold); }
}
