package it.florentino.dark.timeplanapp;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Starter extends Application {
    @Override
    public void start(Stage stage) {
        ScenePlayer player = new ScenePlayer(stage);
        player.showScene("GUI/LoginPage.fxml", "StyleSheets/LoginStyle.css");

    }

    public static void main(String[] args) {
        launch();
    }
}