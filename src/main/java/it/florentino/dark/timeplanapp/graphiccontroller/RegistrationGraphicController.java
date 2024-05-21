package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.ScenePlayer;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegistrationGraphicController {


    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmPassword;

    private ScenePlayer player = ScenePlayer.getScenePlayerInstance(null);
    @FXML
    public void onHyperLinkClicked(){
        try {
            this.player.showScene("GUI/LoginPage.fxml", "StyleSheets/LoginStyle.css");
        }catch(SetSceneException sE){
            System.exit(-1);
        }
    }
    @FXML
    public void onRegisterButtonClick(){}
}
