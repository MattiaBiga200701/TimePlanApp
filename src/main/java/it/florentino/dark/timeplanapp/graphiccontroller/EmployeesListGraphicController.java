package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.appcontroller.EmployeeListController;
import it.florentino.dark.timeplanapp.beans.EmployeeBean;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import it.florentino.dark.timeplanapp.utils.printer.Printer;

import java.util.ArrayList;
import java.util.List;

public class EmployeesListGraphicController extends GraphicController {

    @FXML
    private ChoiceBox<String> contractChoiceBox;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private Button addButton;
    @FXML
    private Label errorLabel;
    @FXML
    private ListView<String> employeesListView;
    private ObservableList<String> employeesItems;

    private EmployeeBean employeeBean;

    private List<EmployeeBean> employeeBeanList;

    private final String[] contractTypes = {"part-time" , "full-time"};




    @FXML
    public void initialize(){

        this.contractChoiceBox.getItems().addAll(this.contractTypes);
        this.employeesItems = FXCollections.observableArrayList();
        this.employeesListView.setItems(this.employeesItems);
        this.employeeBeanList = new ArrayList<>();

    }


    @FXML
    public void onAddClick(){

        String name = this.nameField.getText().trim();
        String surname = this.surnameField.getText().trim();
        String contractType = this.contractChoiceBox.getValue().trim();

        try {


            this.employeeBean = new EmployeeBean(name, surname, ContractTypes.fromString(contractType), this.getLoggedUser().getManagerID());
            this.employeeBeanList.add(this.employeeBean);

            String combination = name + "  " + surname + "  " + contractType;
            this.employeesItems.add(combination);


            this.nameField.clear();
            this.surnameField.clear();
            this.contractChoiceBox.setValue(null);

        }catch(InvalidInputException e){
            this.showError(e.getMessage());
        }


    }

    @FXML
    public void onWorkScheduleClick(){

        try{
            this.getScenePlayer().showScene("GUI/HomePageMan2.fxml");
        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }
    }

    @FXML
    public void onLoadClick(){

        EmployeeListController controller;
        try{
            controller = new EmployeeListController();
            this.employeeBeanList = controller.loadEmployeeList(this.employeeBeanList);

        }catch(InvalidInputException e){
            this.showError(e.getMessage());
        }catch(ServiceException e){
            Printer.perror(e.getMessage());
        }

    }

    @FXML
    public void onRemoveClick(){

        int selectedIdx = this.employeesListView.getSelectionModel().getSelectedIndex();

        String nameToRemove;
        String surnameToRemove;
        String contractTypeToRemove;

        String name;
        String surname;
        ContractTypes contractType;


        if(selectedIdx != -1){

            String selectItem = this.employeesListView.getSelectionModel().getSelectedItem();
            String[] tokens = selectItem.split("\\s{2}");

            nameToRemove = tokens[0];
            surnameToRemove = tokens[1];
            contractTypeToRemove = tokens[2];

            this.employeesListView.getItems().remove(selectedIdx);

            for(EmployeeBean bean: this.employeeBeanList){

                name = bean.getName();
                surname = bean.getSurname();
                contractType = bean.getContractType();

                if(name.equals(nameToRemove) && surname.equals(surnameToRemove) && contractType == ContractTypes.fromString(contractTypeToRemove)){
                    this.employeeBeanList.remove(bean);
                    break;
                }

            }


        }else{
            this.showError("Select an Item");
        }
    }

    public Label getErrorLabel(){
        return this.errorLabel;
    }


}
