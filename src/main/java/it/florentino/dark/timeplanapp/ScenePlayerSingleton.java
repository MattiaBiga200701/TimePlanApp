package it.florentino.dark.timeplanapp;



import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.graphiccontroller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;




public class ScenePlayerSingleton {

    private static ScenePlayerSingleton instance = null;
    private Stage stage;



    private ScenePlayerSingleton(Stage stage){
        this.setStage(stage);
    }


    public static ScenePlayerSingleton getScenePlayerInstance(Stage stage){
        if(ScenePlayerSingleton.instance == null){
            ScenePlayerSingleton.instance = new ScenePlayerSingleton(stage);
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



    public void showScene(String fxmlPath) throws SetSceneException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ScenePlayerSingleton.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            instance.stage.setScene(scene);

        }catch(IllegalStateException | IOException e){
            throw new SetSceneException(e.getMessage());
        }
    }

    public void showRegistrationForm(String fxmlPath, UserBean newUser) throws SetSceneException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ScenePlayerSingleton.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            RegistrationGraphicController graphicController  = fxmlLoader.getController();
            graphicController.setAttribute(newUser);
            Scene scene = new Scene(root);
            instance.stage.setScene(scene);

        }catch(IOException e){
            throw new SetSceneException(e.getMessage());
        }

    }

    public void showEmployeeListPage(String fxmlPath, UserBean loggedUser) throws SetSceneException{

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ScenePlayerSingleton.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            EmployeesListGraphicController controller = fxmlLoader.getController();
            controller.initialize(loggedUser);
            Scene scene = new Scene(root);
            instance.stage.setScene(scene);

        }catch(IOException e){
            throw new SetSceneException(e.getMessage());
        }

    }

    public void showWorkSchedulePage(String fxmlPath, UserBean loggedUser) throws SetSceneException{

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ScenePlayerSingleton.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            WorkScheduleGraphicController controller = fxmlLoader.getController();
            controller.initialize(loggedUser);
            Scene scene = new Scene(root);
            instance.stage.setScene(scene);

        }catch(IOException e){

            throw new SetSceneException(e.getMessage());
        }
    }

    public void showSchedulingViewPage(String fxmlPath, UserBean loggedUser) throws SetSceneException{

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(ScenePlayerSingleton.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            SchedulingViewGraphicController controller = fxmlLoader.getController();
            controller.setAttribute(loggedUser);
            Scene scene  = new Scene(root);
            instance.stage.setScene(scene);
        }catch(IOException e){

            throw new SetSceneException(e.getMessage());

        }
    }

    public void showValidationViewPage(String fxmlPath, UserBean loggedUser) throws SetSceneException{
        try{

            FXMLLoader fxmlLoader = new FXMLLoader(ScenePlayerSingleton.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            ValidationViewGraphicController controller = fxmlLoader.getController();
            controller.setAttribute(loggedUser);
            Scene scene  = new Scene(root);
            instance.stage.setScene(scene);
        }catch(IOException e){

            throw new SetSceneException(e.getMessage());

        }
    }

    public void showEmployeeNotificationsPage(String fxmlPath, UserBean loggedUser) throws SetSceneException{

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(ScenePlayerSingleton.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            EmployeeNotificationGraphicController controller = fxmlLoader.getController();
            controller.setAttribute(loggedUser);
            Scene scene  = new Scene(root);
            instance.stage.setScene(scene);
        }catch(IOException e){

            throw new SetSceneException(e.getMessage());

        }
    }

    public void showManagerNotificationsPage(String fxmlPath, UserBean loggedUser) throws SetSceneException{

        try{

            FXMLLoader fxmlLoader = new FXMLLoader(ScenePlayerSingleton.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            ManagerNotificationGraphicController controller = fxmlLoader.getController();
            controller.setAttribute(loggedUser);
            Scene scene  = new Scene(root);
            instance.stage.setScene(scene);
        }catch(IOException e){

            throw new SetSceneException(e.getMessage());

        }
    }

}
