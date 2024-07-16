package it.florentino.dark.timeplanapp.graphiccontroller;

import it.florentino.dark.timeplanapp.exceptions.SetSceneException;
import it.florentino.dark.timeplanapp.utils.printer.Printer;
import javafx.fxml.FXML;

public abstract class ManagerGraphicController extends GraphicController {


    @FXML
    public void onWorkScheduleClick(){

        try{
            this.getScenePlayer().showWorkSchedulePage("GUI/WorkSchedulePage.fxml", this.getLoggedUser());
        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }

    }

    @FXML
    public void onEmployeeListClick(){

        try{

            this.getScenePlayer().showEmployeeListPage("GUI/EmployeeListPage.fxml", this.getLoggedUser());

        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }

    }

    @FXML
    public void onNotificationsClick(){

        try{

            this.getScenePlayer().showManagerNotificationsPage("GUI/ManagerNotificationsPage.fxml", this.getLoggedUser());

        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }

    }

    @FXML
    public void onViewClick() {

        try{

            this.getScenePlayer().showSchedulingViewPage("GUI/SchedulingViewPage.fxml", this.getLoggedUser());

        }catch(SetSceneException e){
            Printer.perror(e.getMessage());
        }

    }


}
