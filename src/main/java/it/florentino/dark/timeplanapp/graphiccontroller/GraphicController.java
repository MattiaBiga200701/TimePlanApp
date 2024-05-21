package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.ScenePlayer;

public abstract class GraphicController {

    private final ScenePlayer player = ScenePlayer.getScenePlayerInstance(null);

    public ScenePlayer getScenePlayer(){
        return this.player;
    }
}
