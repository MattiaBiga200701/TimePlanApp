package it.florentino.dark.timeplanapp.graphiccontroller;


import it.florentino.dark.timeplanapp.observer.MessageSubject;
import it.florentino.dark.timeplanapp.observer.Observer;
import it.florentino.dark.timeplanapp.appcontroller.WorkScheduleController;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;
import it.florentino.dark.timeplanapp.utils.printer.Printer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class EmployeeNotificationGraphicController extends GraphicController implements Observer {


    @FXML
    private Label errorLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private ListView<String> notificationsListView;

    private WorkScheduleController controller;

    private MessageSubject message;

    @FXML
    public void initialize(){

        this.controller = new WorkScheduleController();

        this.message = MessageSubject.getInstance();

        this.message.attach(this);

        this.messageLabel.setVisible(false);



    }


    @FXML
    public void onValidationViewClick(){

        try{

            this.message.detach(this);
            this.getScenePlayer().showValidationViewPage("GUI/ValidationViewPage.fxml", this.getLoggedUser());

        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }
    }

    @FXML
    public void onRefreshClick(){

    }

    public void update(UserBean sender){

        if(sender.getRole() == Role.MANAGER && sender.getManagerID() == this.getLoggedUser().getManagerID()){
            this.messageLabel.setVisible(true);
        }

    }



    public Label getErrorLabel() {
        return this.errorLabel;
    }

}
