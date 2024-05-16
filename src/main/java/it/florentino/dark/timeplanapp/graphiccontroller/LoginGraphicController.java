package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.ScenePlayer;
import it.florentino.dark.timeplanapp.appcontroller.LoginController;
import it.florentino.dark.timeplanapp.beans.LoginBean;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginGraphicController {


    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorLabel;
    private ScenePlayer player = ScenePlayer.getScenePlayerInstance(null);
    @FXML
    public void onHyperLinkClicked() {
        try {
            this.player.showScene("GUI/RegistrationPage.fxml", "StyleSheets/RegistrationStyle.css");
        } catch(SetSceneException sE) {
           System.err.println(sE.getMessage()); //Da gestire graficamente
        }
    }

    @FXML
    public void onLoginClick(){

        String user = this.username.getText().trim();
        String pass = this.password.getText().trim();

        LoginBean credentials = new LoginBean(user, pass);
        LoginController controller = new LoginController();


    }
}
