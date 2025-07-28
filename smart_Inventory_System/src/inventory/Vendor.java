package inventory;

import javafx.beans.property.*;

public class Vendor {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty contact;
    private final StringProperty email;
    private final StringProperty address;

    public Vendor(int id, String name, String contact, String email, String address) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.contact = new SimpleStringProperty(contact);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public StringProperty contactProperty() { return contact; }
    public StringProperty emailProperty() { return email; }
    public StringProperty addressProperty() { return address; }
}
