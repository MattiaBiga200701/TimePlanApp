package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.observer.MessageSubject;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.observer.Observer;
import it.florentino.dark.timeplanapp.utils.printer.Printer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ManagerNotificationGraphicController extends GraphicController implements Observer {


    @FXML
    private Label errorLabel;

    private MessageSubject message;


    @FXML
    public void initialize(){


        this.message = MessageSubject.getInstance();

        this.message.attach(this);

    }

    @FXML
    public void onEmployeeListClick(){
        try{
            this.message.detach(this);
            this.getScenePlayer().showEmployeeListPage("GUI/EmployeeListPage.fxml", this.getLoggedUser());

        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }
    }

    @FXML
    public void onWorkScheduleClick(){
        try{
            this.message.detach(this);
            this.getScenePlayer().showEmployeeListPage("GUI/WorkSchedulePage.fxml", this.getLoggedUser());

        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }
    }

    @FXML
    public void onViewClick(){

        try{
            this.message.detach(this);
            this.getScenePlayer().showSchedulingViewPage("GUI/SchedulingViewPage.fxml", this.getLoggedUser());

        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }

    }

    public void update(UserBean sender){

    }

    @FXML
    public void onRefreshClick(){}

    public Label getErrorLabel(){
        return this.errorLabel;
    }

}
