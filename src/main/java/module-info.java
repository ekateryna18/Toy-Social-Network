module controller {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens domain to javafx.fxml;
    exports domain;

    opens controller to javafx.fxml;
    exports controller;
}