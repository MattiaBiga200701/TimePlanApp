package it.florentino.dark.timeplanapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScenePlayer {

    private Stage stage;
    private static int sceneWidth;
    private static int sceneHeight;

    public ScenePlayer(Stage stage){
        ScenePlayer.setSceneHeight(1024);
        ScenePlayer.setSceneWidth(1440);
        this.setStage(stage);
        this.stage.setResizable(false);
    }

    public ScenePlayer(Stage stage, int width, int height){
        this(stage);
        ScenePlayer.setSceneWidth(width);
        ScenePlayer.setSceneHeight(height);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void setSceneHeight(int sceneHeight) {
        ScenePlayer.sceneHeight = sceneHeight;
    }


    public static void setSceneWidth(int sceneWidth) {
        ScenePlayer.sceneWidth = sceneWidth;
    }

    public void showScene(String fxmlPath, String cssPath){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ScenePlayer.class.getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader.load(), ScenePlayer.sceneWidth, ScenePlayer.sceneHeight);
            if(cssPath != null){
                String cssURL = this.getClass().getResource(cssPath).toExternalForm();
                scene.getStylesheets().add(cssURL);
            }
            this.stage.setScene(scene);
            this.stage.show();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
