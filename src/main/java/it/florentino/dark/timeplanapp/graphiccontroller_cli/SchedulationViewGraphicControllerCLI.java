package it.florentino.dark.timeplanapp.graphiccontroller_cli;

import it.florentino.dark.timeplanapp.appcontroller.WorkScheduleController;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.beans.WorkShiftBean;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.utils.printer.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class SchedulationViewGraphicControllerCLI extends GenericGraphicControllerCLI {

    private WorkScheduleController controllerAppl;
    public void start(UserBean loggedUser){

        this.controllerAppl = new WorkScheduleController();
        this.setLoggedUser(loggedUser);
        this.showMenu();
    }

    public void showMenu(){

        int choice;

        while(true){

            try{

                Printer.printf("\nSchedulation View");
                Printer.printf("1) Remove Shift");
                Printer.printf("2) Notify Employees");
                Printer.printf("3) Back to Home");
                Printer.printf("4) Quit");

                choice = this.getChoice(1, 4);

                switch(choice){

                    case 1 -> this.removeShift();
                    case 2 -> Printer.printf("Notify Employees");
                    case 3 -> new ManagerHomeGraphicControllerCLI().start(this.getLoggedUser());
                    case 4 -> System.exit(1);
                    default -> throw new InvalidInputException("Invalid choice");
                }

            }catch(InvalidInputException e){
                Printer.perror(e.getMessage());
            }
        }

    }

    public void removeShift(){
        int choice;
        List<WorkShiftBean> workShiftBeanList;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int index;


        String shiftTime;
        String name;
        String surname;
        String contract;
        String email;

        while(true) {
            try {

                Printer.print("Enter shift date with this format [yyyy-MM-dd]: ");
                String shiftDate = reader.readLine();

                WorkShiftBean workShiftBeanToRead = new WorkShiftBean(shiftDate, this.getLoggedUser().getManagerID());


                Printer.printf("\nWork Shift Stored");
                workShiftBeanList = this.controllerAppl.workShiftReader(workShiftBeanToRead);

                index = 1;

                for (WorkShiftBean workShiftBean : workShiftBeanList) {

                    shiftTime = workShiftBean.getShiftTime().getId();
                    name = workShiftBean.getEmployeeName();
                    surname = workShiftBean.getEmployeeSurname();
                    contract = workShiftBean.getEmployeeContract().getId();
                    email = workShiftBean.getEmployeeEmail();

                    String item = shiftTime + "  " + name + "  " + surname + "  " + contract + "  " + email;
                    String indexStr = String.valueOf(index);
                    Printer.printf(indexStr + ")  " + item);
                    index++;

                }

                choice = this.getChoice(1, index - 1);
                WorkShiftBean workShiftBeanToRemove = workShiftBeanList.get(choice - 1);

                workShiftBeanToRemove.setShiftDate(shiftDate);
                workShiftBeanToRemove.setManagerID(this.getLoggedUser().getManagerID());

                this.controllerAppl.removeWorkShift(workShiftBeanToRemove);
                Printer.printf("Work Shift Removed");

                break;

            }catch(IOException | ServiceException e){
                Printer.perror(e.getMessage());
                System.exit(-1);
            }catch(InvalidInputException e){
                Printer.perror(e.getMessage());
            }
        }
    }
}
