package it.florentino.dark.timeplanapp;


import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.graphiccontroller.GraphicController;
import it.florentino.dark.timeplanapp.graphiccontroller.RegistrationGraphicController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;




public class ScenePlayer {

    private static ScenePlayer instance = null;
    private Stage stage;



    private ScenePlayer(Stage stage){
        this.setStage(stage);
    }


    public static ScenePlayer getScenePlayerInstance(Stage stage){
        if(ScenePlayer.instance == null){
            ScenePlayer.instance = new ScenePlayer(stage);
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setHeight(1024.0);
        this.stage.setWidth(1440.0);
        this.stage.setResizable(false);
    }

    public Stage getStage(){
        return this.stage;
    }



    public void showScene(String fxmlPath) throws SetSceneException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ScenePlayer.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            instance.stage.setScene(scene);
        }catch(IllegalStateException | IOException e){
            throw new SetSceneException("SetSceneException: " + e.getMessage());
        }
    }

    public void showScene(String fxmlPath, UserBean newUser) throws SetSceneException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ScenePlayer.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            RegistrationGraphicController graphicController  = fxmlLoader.getController();
            graphicController.setAttribute(newUser);
            Scene scene = new Scene(root);
            instance.stage.setScene(scene);
        }catch(IOException e){
            throw new SetSceneException("SetSceneException:" + e.getMessage());
        }

    }
}
