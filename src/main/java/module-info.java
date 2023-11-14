module ryanpeterson.inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens ryanpeterson.inventorymanagementsystem to javafx.fxml;
    exports ryanpeterson.inventorymanagementsystem;
}