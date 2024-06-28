package it.florentino.dark.timeplanapp.graphiccontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    private String[] contractTypes = {"Part-Time" , "Full-Time"};


    @FXML
    public void initialize(){

        this.contractChoiceBox.getItems().addAll(this.contractTypes);

    }


    @FXML
    public void onAddClick(){}

    @FXML
    public void onWorkScheduleClick(){}

    public Label getErrorLabel(){
        return this.errorLabel;
    }


}
