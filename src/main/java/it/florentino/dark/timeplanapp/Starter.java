package it.florentino.dark.timeplanapp;

import javafx.application.Application;
import javafx.stage.Stage;


public class Starter extends Application {
    @Override
    public void start(Stage stage) {

        ScenePlayer player = ScenePlayer.getScenePlayerInstance(stage);    //SINGLETON
        player.showScene("GUI/LoginPage.fxml", "StyleSheets/LoginStyle.css");

    }

    public static void main(String[] args) {
        launch();
    }
}