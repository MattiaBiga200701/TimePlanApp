package it.florentino.dark.timeplanapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class ScenePlayer {

    private static ScenePlayer instance = null;
    private Stage stage;
    private static int sceneWidth;
    private static int sceneHeight;

    private ScenePlayer(Stage stage){
        ScenePlayer.setSceneHeight(1024);
        ScenePlayer.setSceneWidth(1440);
        this.setStage(stage);
    }

    public ScenePlayer(Stage stage, int width, int height){   //Da eliminare se non servirà
        this(stage);
        ScenePlayer.setSceneWidth(width);
        ScenePlayer.setSceneHeight(height);
    }

    public static ScenePlayer getScenePlayerInstance(Stage stage){
        if(ScenePlayer.instance == null){
            ScenePlayer.instance = new ScenePlayer(stage);
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setResizable(false);
    }

    public Stage getStage(){
        return this.stage;
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
            Stage stage = instance.getStage();
            if(cssPath != null){
                String cssURL = ScenePlayer.class.getResource(cssPath).toExternalForm();
                scene.getStylesheets().add(cssURL);
            }
            stage.setScene(scene);
            stage.show();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
