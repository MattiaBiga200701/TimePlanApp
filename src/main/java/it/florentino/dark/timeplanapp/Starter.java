package it.florentino.dark.timeplanapp;

import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.utils.printer.Printer;
import javafx.application.Application;
import javafx.stage.Stage;


public class Starter extends Application {
    @Override
    public void start(Stage stage) {

        try {
            ScenePlayer player = ScenePlayer.getScenePlayerInstance(stage);    //SINGLETON
            player.showScene("GUI/LoginPage.fxml");
            stage.show();
        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}