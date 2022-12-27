module de.ulewu {
    requires javafx.controls;
    requires javafx.fxml;

    opens de.ulewu to javafx.fxml;
    exports de.ulewu;
}
