module com.example.library {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.library to javafx.fxml;
    exports com.example.library;
}