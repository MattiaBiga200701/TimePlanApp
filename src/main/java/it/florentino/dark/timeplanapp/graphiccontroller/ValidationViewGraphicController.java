package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.appcontroller.WorkScheduleController;
import it.florentino.dark.timeplanapp.beans.NotificationBean;
import it.florentino.dark.timeplanapp.beans.WorkShiftBean;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import it.florentino.dark.timeplanapp.utils.printer.Printer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ValidationViewGraphicController extends GraphicController{

    @FXML
    private DatePicker schedulingDate;
    @FXML
    private Label errorLabel;
    @FXML
    private ListView<String> workShiftListView;
    @FXML
    private Button declineButton;
    @FXML
    private Button approveButton;




    private WorkScheduleController controller;

    private List<WorkShiftBean> workShiftBeanList;


    @FXML
    public void initialize(){

        this.controller = new WorkScheduleController();

        this.workShiftBeanList = new ArrayList<>();

        this.declineButton.setOnAction(this::onPreviousClick);

        this.approveButton.setOnAction(this::onPreviousClick);



    }
    @FXML
    public void onSearchClick(){

        LocalDate schedulingDatePicker = this.schedulingDate.getValue();
        WorkShiftBean workShiftToRead;
        String schedulingDateStr;
        ObservableList<String> items = FXCollections.observableArrayList();


        try{

            if(schedulingDatePicker == null ){
                throw new InvalidInputException("Select a date");
            }


            schedulingDateStr = schedulingDatePicker.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            workShiftToRead = new WorkShiftBean(schedulingDateStr, this.getLoggedUser().getManagerID());
            this.workShiftBeanList = this.controller.workShiftReader(workShiftToRead, this.getLoggedUser());

            for(WorkShiftBean workShiftRead : workShiftBeanList){

                String item = this.setItem(workShiftRead);
                items.add(item);
                this.workShiftListView.setItems(items);
            }

            this.declineButton.setOnAction(this::onDeclineClick);

            this.approveButton.setOnAction(this::onApproveClick);


        }catch(InvalidInputException e){
            this.showError(e.getMessage());
        }catch(ServiceException e){
            Printer.perror(e.getMessage());
        }


    }

    @FXML
    public void onPreviousClick(ActionEvent event){

        this.showError("Search Schedulation!");
    }


    @FXML
    public void onDeclineClick(ActionEvent event){

        LocalDate schedulingDatePicker = this.schedulingDate.getValue();

        try{

            String schedulingDateStr = schedulingDatePicker.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String newMessage = "Scheduling declined at date: " + schedulingDateStr;
            NotificationBean newNotificationBean = new NotificationBean(newMessage, this.getLoggedUser().getRole(), this.getLoggedUser().getManagerID());
            newNotificationBean = this.controller.insertMessage(newNotificationBean, this.getLoggedUser());

            if(newNotificationBean == null){
                Printer.perror("Notify error");
            }

            this.declineButton.setOnAction(this::onPreviousClick);

            this.approveButton.setOnAction(this::onPreviousClick);

            this.showError("Manager notified");

        }catch(InvalidInputException e){
            this.showError(e.getMessage());
        }catch(ServiceException e){
            Printer.perror(e.getMessage());
        }


    }

    @FXML
    public void onApproveClick(ActionEvent event){

        this.showError("Scheduling Approved");

        this.declineButton.setOnAction(this::onPreviousClick);

        this.approveButton.setOnAction(this::onPreviousClick);
    }

    @FXML
    public void onNotificationsClick(){

        try{

            this.getScenePlayer().showEmployeeNotificationsPage("GUI/EmployeeNotificationsPage.fxml", this.getLoggedUser());

        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }
    }

    public Label getErrorLabel(){
        return this.errorLabel;
    }


}
