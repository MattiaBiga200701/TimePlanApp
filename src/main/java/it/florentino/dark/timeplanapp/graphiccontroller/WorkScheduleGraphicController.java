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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.util.List;

public class WorkScheduleGraphicController extends GraphicController{

    @FXML
    private ListView<String> employeesListView;

    @FXML
    private GridPane schedulationTable;
    private List<EmployeeBean> employeesBeanList;
    private ObservableList<String> employeeItems;
    private WorkScheduleController controller;





    @FXML
    private Label errorLabel;

    @FXML
    public void initialize(UserBean loggedUser){

        this.controller = new WorkScheduleController();
        this.setAttribute(loggedUser);
        this.employeeItems = FXCollections.observableArrayList();
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
    public void onEmployeeListClick(){
        try{

            this.getScenePlayer().showHomePageMan("GUI/HomePageMan.fxml", this.getLoggedUser());

        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }
    }

    @FXML
    public void onSchedulationClick(){

    }

    public Label getErrorLabel(){ return this.errorLabel; }
}
