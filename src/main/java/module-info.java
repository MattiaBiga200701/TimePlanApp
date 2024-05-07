module it.florentino.dark.timeplanapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens it.florentino.dark.timeplanapp to javafx.fxml;
    exports it.florentino.dark.timeplanapp;
    exports it.florentino.dark.timeplanapp.graphiccontroller;
    opens it.florentino.dark.timeplanapp.graphiccontroller to javafx.fxml;
    exports it.florentino.dark.timeplanapp.exceptions;

}