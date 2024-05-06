package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.ScenePlayer;
import javafx.fxml.FXML;


public class LoginGraphicController {

    ScenePlayer player = ScenePlayer.getScenePlayerInstance(null);
    @FXML
    public void onHyperLinkClicked(){
        player.showScene("GUI/RegistrationPage.fxml", "StyleSheets/RegistrationStyle.css");
    }
}
