package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.appcontroller.WorkScheduleController;
import it.florentino.dark.timeplanapp.beans.EmployeeBean;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;

import it.florentino.dark.timeplanapp.utils.printer.Printer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


import java.time.LocalDate;
import java.util.List;

public class WorkScheduleGraphicController extends GraphicController{

    @FXML
    private ListView<String> employeesListView;

    @FXML
    private ChoiceBox<String> shiftTimeChoice;
    @FXML
    private DatePicker shiftDatePicker;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label contractLabel;


    private List<EmployeeBean> employeesBeanList;
    private ObservableList<String> employeeItems;
    private WorkScheduleController controller;

    private final String[] items = {"8:00 - 9:00", "9:00 - 10:00", "10:00 - 11:00",
                                    "11:00 - 12:00", "12:00 - 13:00", "14:00 - 15:00",
                                    "15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00"};



    @FXML
    private Label errorLabel;

    @FXML
    public void initialize(UserBean loggedUser){

        this.controller = new WorkScheduleController();
        this.setAttribute(loggedUser);

        this.employeeItems = FXCollections.observableArrayList();

       this.shiftTimeChoice.getItems().addAll(items);

        String name;
        String surname;
        String contractType;
        String item;
        try {

            this.employeesBeanList = this.controller.loadEmployeeList(this.getLoggedUser());
            for(EmployeeBean employeeBeanStored : this.employeesBeanList){
                name = employeeBeanStored.getName();
                surname = employeeBeanStored.getSurname();
                contractType = employeeBeanStored.getContractType().getId();
                item = name + "  " + surname + "  " + contractType;
                this.employeeItems.add(item);
                this.employeesListView.setItems(this.employeeItems);
            }
        }catch(ServiceException e){
            Printer.perror(e.getMessage());
        }catch(InvalidInputException e){
            this.showError(e.getMessage());
        }


    }

    @FXML
    public void onDoubleClick(MouseEvent event){
        if(event.getClickCount() == 2) {
            String selectedItem = this.employeesListView.getSelectionModel().getSelectedItem();
            String[] tokens = selectedItem.split("\\s{2}");
            this.nameLabel.setText(tokens[0]);
            this.surnameLabel.setText(tokens[1]);
            this.contractLabel.setText(tokens[2]);
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

    @FXML
    public void onAddShiftClick(){

        String employeeName = this.nameLabel.getText();
        String employeeSurname = this.surnameLabel.getText();
        String employeeContract = this.contractLabel.getText();
        LocalDate shiftDate = this.shiftDatePicker.getValue();
        String shiftTime = this.shiftTimeChoice.getValue();

        try{

            if(shiftTime.isEmpty()){
                throw new InvalidInputException("Select shift time");
            }

            if(shiftDate == null){
                throw new InvalidInputException("Select a date");
            }

        }catch(InvalidInputException e){
            this.showError(e.getMessage());
        }
    }

    public Label getErrorLabel(){ return this.errorLabel; }
}
