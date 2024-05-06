package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.ScenePlayer;
import javafx.fxml.FXML;

public class RegistrationGraphicController {

    ScenePlayer player = ScenePlayer.getScenePlayerInstance(null);
    @FXML
    public void onHyperLinkClicked(){
        player.showScene("GUI/LoginPage.fxml", "StyleSheets/LoginStyle.css");
    }
}
