package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
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
    private Button addButton;
    @FXML
    private Label errorLabel;
    @FXML
    private ListView<String> employeesListView;
    private ObservableList<String> employeesItems;

    private final String[] contractTypes = {"Part-Time" , "Full-Time"};



    @FXML
    public void initialize(){

        this.contractChoiceBox.getItems().addAll(this.contractTypes);
        this.employeesItems = FXCollections.observableArrayList();
        this.employeesListView.setItems(this.employeesItems);

    }


    @FXML
    public void onAddClick(){

        String name = this.nameField.getText();
        String surname = this.surnameField.getText();
        String contractType = this.contractChoiceBox.getValue();

        try {
            if (name.contains("  ") || surname.contains("  ")) {
                throw new InvalidInputException("Invalid Input");
            }
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

    public Label getErrorLabel(){
        return this.errorLabel;
    }


}
