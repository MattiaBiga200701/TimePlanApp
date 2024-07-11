package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.appcontroller.WorkScheduleController;
import it.florentino.dark.timeplanapp.beans.WorkShiftBean;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
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

public class SchedulationViewGraphicController extends GraphicController{

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

    private ObservableList<String> items;

    private List<WorkShiftBean> workShiftBeanList;

    private WorkScheduleController controller;


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
        this.items = FXCollections.observableArrayList();

        try{

            if(shiftDatePickerValue == null){
                throw new InvalidInputException("Select a date");
            }

            shiftDate = shiftDatePickerValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            workShiftsToRead = new WorkShiftBean(shiftDate, this.getLoggedUser().getManagerID());
            this.workShiftBeanList = this.controller.workShiftReader(workShiftsToRead);

            for(WorkShiftBean workShiftBeanRead: this.workShiftBeanList){

                String item = this.setItem(workShiftBeanRead);
                this.items.add(item);
                this.workShiftListView.setItems(this.items);

            }



        }catch(InvalidInputException e){
            this.showError(e.getMessage());
        }catch(ServiceException e){
            Printer.perror(e.getMessage());
        }

        this.removeButton.setOnAction(this::onRemoveClick);
        this.notifyButton.setOnAction(this::onNotifyClick);

    }

    private String setItem(WorkShiftBean workShiftBeanRead) {

        String shiftTime = workShiftBeanRead.getShiftTime().getId();
        String employeeName = workShiftBeanRead.getEmployeeName();
        String employeeSurname = workShiftBeanRead.getEmployeeSurname();
        String employeeContract = workShiftBeanRead.getEmployeeContract().getId();
        String employeeEmail = workShiftBeanRead.getEmployeeEmail();

        return shiftTime + "  " + employeeName + "  " + employeeSurname + "  " + employeeContract + "  " + employeeEmail;

    }

    @FXML
    public void onNotifyClick(ActionEvent event){}

    @FXML
    public void onPreviousClick(ActionEvent event){

        this.showError("Search Schedulation!");
    }

    @FXML
    public void onRemoveClick(ActionEvent event){

        int selectedIdx = this.workShiftListView.getSelectionModel().getSelectedIndex();

        LocalDate shiftDatePickerValue = this.shiftDatePicker.getValue();
        String shiftDate = shiftDatePickerValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

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

    public Label getErrorLabel(){
        return this.errorLabel;
    }

}
