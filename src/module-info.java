module JAVA.FX.SQL {

    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;

    opens sample;
    opens Handlers.Model to javafx.base;
}