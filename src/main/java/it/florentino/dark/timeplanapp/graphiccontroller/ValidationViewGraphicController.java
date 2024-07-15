package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.appcontroller.WorkScheduleController;
import it.florentino.dark.timeplanapp.beans.WorkShiftBean;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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



    }
    @FXML
    public void onSearchClick(){

        LocalDate schedulingDatePicker = this.schedulingDate.getValue();
        WorkShiftBean workShiftToRead;
        String schedulingDate;
        ObservableList<String> items = FXCollections.observableArrayList();
        this.declineButton.setDisable(true);
        this.approveButton.setDisable(true);

        try{

            if(schedulingDatePicker == null ){
                throw new InvalidInputException("Select a date");
            }

            this.declineButton.setDisable(false);
            this.approveButton.setDisable(false);

            schedulingDate = schedulingDatePicker.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            workShiftToRead = new WorkShiftBean(schedulingDate, this.getLoggedUser().getManagerID());
            this.workShiftBeanList = this.controller.workShiftReader(workShiftToRead, this.getLoggedUser());

            for(WorkShiftBean workShiftRead : workShiftBeanList){

                String item = this.setItem(workShiftRead);
                items.add(item);
                this.workShiftListView.setItems(items);
            }


        }catch(InvalidInputException e){
            this.showError(e.getMessage());
        }catch(ServiceException e){
            Printer.perror(e.getMessage());
        }

        this.schedulingDate.setValue(null);
    }


    @FXML
    public void onDeclineClick(){



    }

    @FXML
    public void onApproveClick(){

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
