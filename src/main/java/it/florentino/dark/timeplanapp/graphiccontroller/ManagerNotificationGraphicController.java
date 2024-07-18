package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.appcontroller.WorkScheduleController;
import it.florentino.dark.timeplanapp.beans.NotificationBean;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.observer.MessageSubject;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.observer.Observer;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;
import it.florentino.dark.timeplanapp.utils.printer.Printer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class ManagerNotificationGraphicController extends ManagerGraphicController implements Observer {


    @FXML
    private Label errorLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private ListView<String> notificationsListView;

    private WorkScheduleController controller;

    private MessageSubject message;


    @FXML
    public void initialize(UserBean loggedUser){

        this.controller = new WorkScheduleController();

        this.setAttribute(loggedUser);

        this.message = MessageSubject.getInstance();

        this.message.attach(this);

        this.messageLabel.setVisible(false);

        try{
            this.readMessage(this.getLoggedUser());
        }catch(InvalidInputException e){
            this.showError(e.getMessage());
        }catch(ServiceException e){
            Printer.perror(e.getMessage());
        }


    }

    private void readMessage(UserBean loggedUser) throws InvalidInputException, ServiceException{

        ObservableList<String> items = FXCollections.observableArrayList();


        List<NotificationBean> notifications = this.controller.readMessages(loggedUser);

        for(NotificationBean notification : notifications){
            String messageRead = notification.getMessage();
            items.add(messageRead);
            this.notificationsListView.setItems(items);
        }


    }


    @Override
    @FXML
    public void onEmployeeListClick(){
        try{
            this.message.detach(this);
            this.getScenePlayer().showEmployeeListPage("GUI/EmployeeListPage.fxml", this.getLoggedUser());

        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }
    }

    @Override
    @FXML
    public void onWorkScheduleClick(){
        try{
            this.message.detach(this);
            this.getScenePlayer().showWorkSchedulePage("GUI/WorkSchedulePage.fxml", this.getLoggedUser());

        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }
    }

    @Override
    @FXML
    public void onViewClick(){

        try{
            this.message.detach(this);
            this.getScenePlayer().showSchedulingViewPage("GUI/SchedulingViewPage.fxml", this.getLoggedUser());

        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }

    }

    public void update(){
        UserBean sender = this.message.getState();
        if(sender.getRole() == Role.EMPLOYEE && sender.getManagerID() == this.getLoggedUser().getManagerID()){
            this.messageLabel.setVisible(true);
        }
    }

    @FXML
    public void onRefreshClick(){

        this.messageLabel.setVisible(false);

        try{

            this.readMessage(this.getLoggedUser());
        }catch(InvalidInputException e){
            this.showError(e.getMessage());
        }catch(ServiceException e){
            Printer.perror(e.getMessage());
        }


    }

    public Label getErrorLabel(){
        return this.errorLabel;
    }

}
