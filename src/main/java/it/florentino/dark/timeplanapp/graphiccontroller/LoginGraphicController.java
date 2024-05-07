package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.ScenePlayer;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import javafx.fxml.FXML;


public class LoginGraphicController {

    ScenePlayer player = ScenePlayer.getScenePlayerInstance(null);
    @FXML
    public void onHyperLinkClicked() throws RuntimeException {
        try {
            player.showScene("GUI/RegistrationPage.fxml", "StyleSheets/RegistrationStyle.css");
        } catch(SetSceneException sE) {
           throw new RuntimeException(sE); //Da gestire graficamente
        }
    }
}
