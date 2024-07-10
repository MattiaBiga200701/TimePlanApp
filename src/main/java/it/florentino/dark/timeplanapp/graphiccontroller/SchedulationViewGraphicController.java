package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.appcontroller.WorkScheduleController;
import it.florentino.dark.timeplanapp.beans.WorkShiftBean;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.utils.printer.Printer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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

    private ObservableList<String> items;

    private List<WorkShiftBean> workShiftBeanList;

    private WorkScheduleController controller;


    @FXML
    public void initialize(){

        this.controller = new WorkScheduleController();

        this.workShiftBeanList = new ArrayList<>();

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

                String shiftTime = workShiftBeanRead.getShiftTime().getId();
                String employeeName = workShiftBeanRead.getEmployeeName();
                String employeeSurname = workShiftBeanRead.getEmployeeSurname();
                String employeeContract = workShiftBeanRead.getEmployeeContract().getId();

                String item = shiftTime + "  " + employeeName + "  " + employeeSurname + "  " + employeeContract;
                Printer.printf(item);
                this.items.add(item);
                this.workShiftListView.setItems(this.items);
            }



        }catch(InvalidInputException e){
            this.showError(e.getMessage());
        }catch(ServiceException e){
            Printer.perror(e.getMessage());
        }

    }

    @FXML
    public void onLoadClick(){}

    @FXML
    public void onRemoveClick(){}



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
