package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.beans.EmployeeBean;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
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

    private final String[] contractTypes = {"Part-Time" , "Full-Time"};



    @FXML
    public void initialize(){

        this.contractChoiceBox.getItems().addAll(this.contractTypes);
        this.employeesItems = FXCollections.observableArrayList();
        this.employeesListView.setItems(this.employeesItems);

    }


    @FXML
    public void onAddClick(){

        String name = this.nameField.getText().trim();
        String surname = this.surnameField.getText().trim();
        String contractType = this.contractChoiceBox.getValue().trim();

        try {

            this.employeeBeanList = new ArrayList<>();
            this.employeeBean = new EmployeeBean(name, surname, ContractTypes.fromString(contractType));
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



    }

    @FXML
    public void onRemoveClick(){

    }

    public Label getErrorLabel(){
        return this.errorLabel;
    }


}
