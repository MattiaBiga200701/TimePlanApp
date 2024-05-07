package it.florentino.dark.timeplanapp;

import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import javafx.application.Application;
import javafx.stage.Stage;


public class Starter extends Application {
    @Override
    public void start(Stage stage) {

        try {
            ScenePlayer player = ScenePlayer.getScenePlayerInstance(stage);    //SINGLETON
            player.showScene("GUI/LoginPage.fxml", "StyleSheets/LoginStyle.css");
        }catch(SetSceneException e){
            throw new RuntimeException(e); //Gestione da terminale da cambiare
        }
    }

    public static void main(String[] args) {
        launch();
    }
}