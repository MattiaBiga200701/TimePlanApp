module it.florentino.dark.timeplanapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens it.florentino.dark.timeplanapp to javafx.fxml;
    exports it.florentino.dark.timeplanapp;
    exports it.florentino.dark.timeplanapp.GraphicController;
    opens it.florentino.dark.timeplanapp.GraphicController to javafx.fxml;
}