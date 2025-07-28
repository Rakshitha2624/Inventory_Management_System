## Inventory Management System

A desktop-based JavaFX application to manage inventory operations such as product tracking, vendor management, low stock alerts, and backup/restore capabilities. The system is designed for small businesses to easily manage their stock data using a clean user interface and SQLite database.

###  Objective

To develop a **GUI-based Inventory Management System** that supports:

* Adding, viewing, and updating product entries
* Vendor registration and mapping
* Stock in/out tracking
* Low-stock detection and alerting
* Dashboard charts for visual insight
* Generating restock reports
* Backup and restore of the SQLite database

### Tools & Technologies Used

* **Java (JDK 22)**
* **JavaFX (FXML UI )**
* **SQLite (lightweight embedded DB)**
* **Eclipse IDE**
* **JDBC for database connectivity**

###  Key Concepts Implemented

* JavaFX UI Design (FXML)
* Object-Oriented Programming
* MVC Structure: Controllers manage logic, FXML defines layout
* Java Collections (Lists, Arrays)
* SQL Queries for CRUD operations
* File I/O for backup/restore
* JavaFX Charts (BarChart & PieChart)
* Exception handling and input validation

###  How to Run

1. **Open Eclipse IDE**

2. **Create a New JavaFX Project**

3. **Import or Create Classes and FXML Files**

   * Add all required `.java` files in the `src/inventory` package
   * Create UI using FXML files like `Login.fxml`, `Dashboard.fxml` using SceneBuilder

4. **Add JavaFX and SQLite JARs**

   * Include `javafx-sdk/lib` to module path
   * Include `sqlite-jdbc-<version>.jar` to classpath

5. **Run the App**

   * Right-click `Main.java` → Run As → Java Application
   * Login with credentials and explore all features
