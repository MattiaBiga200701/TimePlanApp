package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.utils.printer.Printer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SchedulationViewGraphicController extends GraphicController{

    @FXML
    private Label errorLabel;



    @FXML
    public void onSearchClick(){}

    @FXML
    public void onLoadClick(){}

    @FXML
    public void onRemoveClick(){}

    public Label getErrorLabel(){
        return this.errorLabel;
    }

    @FXML
    public void onWorkScheduleClick(){

        try{
            this.getScenePlayer().showHomePageMan2("GUI/HomePageMan2.fxml", this.getLoggedUser());
        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }

    }

    @FXML
    public void onEmployeeListClick(){

        try{

            this.getScenePlayer().showHomePageMan("GUI/HomePageMan.fxml", this.getLoggedUser());

        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }

    }

}
