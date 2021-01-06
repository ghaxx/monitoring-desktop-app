module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;

    opens ghx to javafx.fxml;
    exports ghx;
}