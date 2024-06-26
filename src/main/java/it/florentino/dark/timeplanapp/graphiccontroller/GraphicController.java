package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.ScenePlayer;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public abstract class GraphicController {

    private final ScenePlayer player = ScenePlayer.getScenePlayerInstance(null);

    protected abstract Label getErrorLabel();

    public void showError(String message){
        Label errorLabel = this.getErrorLabel();
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> errorLabel.setVisible(false));
        pause.play();

    }
    public ScenePlayer getScenePlayer(){
        return this.player;
    }
}
