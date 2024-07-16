package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.appcontroller.WorkScheduleController;
import it.florentino.dark.timeplanapp.beans.NotificationBean;
import it.florentino.dark.timeplanapp.beans.WorkShiftBean;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;
import it.florentino.dark.timeplanapp.utils.enumaration.ShiftSlots;
import it.florentino.dark.timeplanapp.utils.printer.Printer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SchedulingViewGraphicController extends ManagerGraphicController {

    @FXML
    private Label errorLabel;
    @FXML
    private ListView<String> workShiftListView;

    @FXML
    private DatePicker shiftDatePicker;

    @FXML
    private Button removeButton;

    @FXML
    private Button notifyButton;

    private List<WorkShiftBean> workShiftBeanList;

    private WorkScheduleController controller;

    private static final String dateFormat = "yyyy-MM-dd";


    @FXML
    public void initialize(){

        this.controller = new WorkScheduleController();

        this.workShiftBeanList = new ArrayList<>();

        this.removeButton.setOnAction(this::onPreviousClick);

        this.notifyButton.setOnAction(this::onPreviousClick);

    }

    @FXML
    public void onSearchClick(){

        LocalDate shiftDatePickerValue = this.shiftDatePicker.getValue();
        WorkShiftBean workShiftsToRead;
        String shiftDate;
        ObservableList<String> items = FXCollections.observableArrayList();

        try{

            if(shiftDatePickerValue == null){
                throw new InvalidInputException("Select a date");
            }

            shiftDate = shiftDatePickerValue.format(DateTimeFormatter.ofPattern(dateFormat));

            workShiftsToRead = new WorkShiftBean(shiftDate, this.getLoggedUser().getManagerID());
            this.workShiftBeanList = this.controller.workShiftReader(workShiftsToRead, this.getLoggedUser());

            for(WorkShiftBean workShiftBeanRead: this.workShiftBeanList){

                String item = this.setItem(workShiftBeanRead);
                items.add(item);
                this.workShiftListView.setItems(items);

            }



        }catch(InvalidInputException e){
            this.showError(e.getMessage());
        }catch(ServiceException e){
            Printer.perror(e.getMessage());
        }

        this.removeButton.setOnAction(this::onRemoveClick);
        this.notifyButton.setOnAction(this::onNotifyClick);

    }

    @FXML
    public void onNotifyClick(ActionEvent event){

        LocalDate schedulingDate = this.shiftDatePicker.getValue();

        try{

            String schedulingDateStr = schedulingDate.format(DateTimeFormatter.ofPattern(dateFormat));
            String newMessage = "New scheduling written for the date: " + schedulingDateStr;
            NotificationBean newNotificationBean = new NotificationBean(newMessage, this.getLoggedUser().getRole(), this.getLoggedUser().getManagerID());
            newNotificationBean = this.controller.insertMessage(newNotificationBean, this.getLoggedUser());

            if(newNotificationBean == null){
                Printer.perror("Notify error");
            }

            this.removeButton.setOnAction(this::onPreviousClick);

            this.notifyButton.setOnAction(this::onPreviousClick);

            this.showError("Employees notified");

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
    public void onRemoveClick(ActionEvent event){

        int selectedIdx = this.workShiftListView.getSelectionModel().getSelectedIndex();

        LocalDate shiftDatePickerValue = this.shiftDatePicker.getValue();
        String shiftDate = shiftDatePickerValue.format(DateTimeFormatter.ofPattern(dateFormat));

        if(selectedIdx != -1){

            String selectItem = this.workShiftListView.getSelectionModel().getSelectedItem();
            String[] tokens = selectItem.split("\\s{2}");

            String shiftTime = tokens[0];
            String employeeName = tokens[1];
            String employeeSurname = tokens[2];
            String employeeContract = tokens[3];
            String employeeEmail = tokens[4];

            this.workShiftListView.getItems().remove(selectedIdx);

            try {


                WorkShiftBean workShiftBeanToRemove = new WorkShiftBean(ShiftSlots.fromString(shiftTime), shiftDate, employeeName, employeeSurname, ContractTypes.fromString(employeeContract), employeeEmail, this.getLoggedUser().getManagerID());
                this.controller.removeWorkShift(workShiftBeanToRemove);

            }catch(InvalidInputException e){
                this.showError(e.getMessage());
            }catch(ServiceException e){
                Printer.perror(e.getMessage());
            }

        }else{
            this.showError("Select an item");
        }
    }


    public Label getErrorLabel(){
        return this.errorLabel;
    }

}
