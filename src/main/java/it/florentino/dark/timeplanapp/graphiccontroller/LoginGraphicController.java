package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.ScenePlayer;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginGraphicController {


    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Label errorLabel;
    ScenePlayer player = ScenePlayer.getScenePlayerInstance(null);
    @FXML
    public void onHyperLinkClicked() {
        try {
            player.showScene("GUI/RegistrationPage.fxml", "StyleSheets/RegistrationStyle.css");
        } catch(SetSceneException sE) {
           System.err.println(sE.getMessage()); //Da gestire graficamente
        }
    }

    @FXML
    public void onLoginClick(){

    }
}
