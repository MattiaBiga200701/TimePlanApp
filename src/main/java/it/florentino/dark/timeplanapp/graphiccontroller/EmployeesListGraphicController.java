package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.appcontroller.EmployeeListController;
import it.florentino.dark.timeplanapp.beans.EmployeeBean;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import it.florentino.dark.timeplanapp.utils.printer.Printer;

public class EmployeesListGraphicController extends GraphicController {

    @FXML
    private ChoiceBox<String> contractChoiceBox;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField emailField;
    @FXML
    private Label errorLabel;
    @FXML
    private ListView<String> employeesListView;
    private ObservableList<String> employeesItems;
    private final String[] contractTypes = {"part-time" , "full-time"};

    private EmployeeListController controller;




    @FXML
    public void initialize(UserBean loggedUser){

        this.contractChoiceBox.getItems().addAll(this.contractTypes);
        this.employeesItems = FXCollections.observableArrayList();
        this.employeesListView.setItems(this.employeesItems);
        this.setAttribute(loggedUser);
        this.controller = new EmployeeListController();


    }


    @FXML
    public void onAddClick(){

        String name = this.nameField.getText().trim();
        String surname = this.surnameField.getText().trim();
        String email = this.emailField.getText().trim();

        try {

            if(contractChoiceBox.getValue() == null){
                throw new InvalidInputException("Select Contract Type");
            }

            String contractType = this.contractChoiceBox.getValue();


            EmployeeBean employeeBean = new EmployeeBean(name, surname, ContractTypes.fromString(contractType), email,  this.getLoggedUser().getManagerID());

            String combination = name + "  " + surname + "  " + contractType + "  " + email;

            this.controller.insertEmployee(employeeBean);
            this.employeesItems.add(combination);

            this.nameField.clear();
            this.surnameField.clear();
            this.emailField.clear();
            this.contractChoiceBox.setValue(null);

        }catch(InvalidInputException e){
            this.showError(e.getMessage());
        }catch(ServiceException e){
            Printer.perror(e.getMessage());
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
    public void onRemoveClick(){

        int selectedIdx = this.employeesListView.getSelectionModel().getSelectedIndex();

        String nameToRemove;
        String surnameToRemove;
        String emailToRemove;
        String contractTypeToRemove;


        if(selectedIdx != -1){

            String selectItem = this.employeesListView.getSelectionModel().getSelectedItem();
            String[] tokens = selectItem.split("\\s{2}");

            nameToRemove = tokens[0];
            surnameToRemove = tokens[1];
            contractTypeToRemove = tokens[2];
            emailToRemove = tokens[3];

            this.employeesListView.getItems().remove(selectedIdx);
            try {

                EmployeeBean employeeBeanToRemove = new EmployeeBean(nameToRemove, surnameToRemove, ContractTypes.fromString(contractTypeToRemove), emailToRemove, this.getLoggedUser().getManagerID());
                this.controller.removeEmployee(employeeBeanToRemove);

            }catch(InvalidInputException e){
                this.showError(e.getMessage());
            }catch(ServiceException e){
                Printer.perror(e.getMessage());
            }




        }else{
            this.showError("Select an Item");
        }
    }

    @FXML
    public void onViewClick(){

        try{

            this.getScenePlayer().showSchedulationViewPage("GUI/SchedulationViewPage.fxml", this.getLoggedUser());

        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }

    }

    public Label getErrorLabel(){
        return this.errorLabel;
    }


}
