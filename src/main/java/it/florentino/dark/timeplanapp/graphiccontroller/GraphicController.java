package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.ScenePlayerSingleton;
import it.florentino.dark.timeplanapp.beans.WorkShiftBean;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;
import it.florentino.dark.timeplanapp.beans.UserBean;

public abstract class GraphicController {

    private final ScenePlayerSingleton player = ScenePlayerSingleton.getScenePlayerInstance(null);

    private UserBean loggedUser;

    protected abstract Label getErrorLabel();

    public void showError(String message){
        Label errorLabel = this.getErrorLabel();
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> errorLabel.setVisible(false));
        pause.play();
    }

    protected String setItem(WorkShiftBean workShiftBeanRead) {

        String shiftTime = workShiftBeanRead.getShiftTime().getId();
        String employeeName = workShiftBeanRead.getEmployeeName();
        String employeeSurname = workShiftBeanRead.getEmployeeSurname();
        String employeeContract = workShiftBeanRead.getEmployeeContract().getId();
        String employeeEmail = workShiftBeanRead.getEmployeeEmail();

        return shiftTime + "  " + employeeName + "  " + employeeSurname + "  " + employeeContract + "  " + employeeEmail;

    }

    public ScenePlayerSingleton getScenePlayer(){
        return this.player;
    }

    public void setAttribute(UserBean loggedUser){
        this.loggedUser = loggedUser;
    }

    public UserBean getLoggedUser(){
        return this.loggedUser;
    }
}
