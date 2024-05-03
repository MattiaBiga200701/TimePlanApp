package it.florentino.dark.timeplanapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Starter extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Starter.class.getResource("GUI/LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 1024);
        String css = this.getClass().getResource("StyleSheets/LoginStyle.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setResizable(false);
        stage.setTitle("TimePlanApp");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}