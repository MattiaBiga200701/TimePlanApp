package it.florentino.dark.timeplanapp.graphiccontroller;


import it.florentino.dark.timeplanapp.appcontroller.WorkScheduleController;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.utils.printer.Printer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class EmployeeNotificationGraphicController extends GraphicController{


    @FXML
    private Label errorLabel;
    @FXML
    private ListView<String> notificationsListView;

    private WorkScheduleController controller;

    @FXML
    public void initialize(){

        this.controller = new WorkScheduleController();

    }


    @FXML
    public void onValidationViewClick(){

        try{
            this.getScenePlayer().showValidationViewPage("GUI/ValidationViewPage.fxml", this.getLoggedUser());
        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }
    }

    @FXML
    public void onRefreshClick(){
    }

    public Label getErrorLabel() {
        return this.errorLabel;
    }

}
