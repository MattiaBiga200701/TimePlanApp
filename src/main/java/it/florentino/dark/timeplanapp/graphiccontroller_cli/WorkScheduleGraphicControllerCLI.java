package it.florentino.dark.timeplanapp.graphiccontroller_cli;

import it.florentino.dark.timeplanapp.appcontroller.WorkScheduleController;
import it.florentino.dark.timeplanapp.beans.EmployeeBean;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.beans.WorkShiftBean;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;
import it.florentino.dark.timeplanapp.utils.enumaration.ShiftSlots;
import it.florentino.dark.timeplanapp.utils.printer.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class WorkScheduleGraphicControllerCLI extends GenericGraphicControllerCLI {

    private WorkScheduleController controller;
    public void start(UserBean loggedUser){

        this.controller = new WorkScheduleController();
        this.setLoggedUser(loggedUser);
        this.showMenu();
    }

    public void showMenu() {

        int choice;
        while (true) {

            try {

                Printer.printf("\n*---- Work Schedule ----*");
                Printer.printf("1) Add Shift");
                Printer.printf("2) Back to Home");
                Printer.printf("3) Quit");
                choice = this.getChoice(1, 3);

                switch (choice) {
                    case 1 -> this.addShift();
                    case 2 -> new ManagerHomeGraphicControllerCLI().start(this.getLoggedUser());
                    case 3 -> System.exit(1);
                    default -> throw new InvalidInputException("Invalid choice");
                }

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }


        }
    }

    public void addShift(){

        int index;
        int choice;
        EmployeeBean employeeBeanSelected;

        String name;
        String surname;
        String contract;
        String email;

        String[] items = {"8:00 - 9:00", "9:00 - 10:00", "10:00 - 11:00",
                "11:00 - 12:00", "12:00 - 13:00", "14:00 - 15:00",
                "15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00"};

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                Printer.printf("\nEmployees Stored");
                List<EmployeeBean> employeeBeanList = this.controller.loadEmployeeList(this.getLoggedUser());

                index = 1;
                for(EmployeeBean employeeBean : employeeBeanList){

                    name = employeeBean.getName();
                    surname = employeeBean.getSurname();
                    contract = employeeBean.getContractType().getId();
                    email = employeeBean.getEmail();


                    String item = name + "  "  + surname + "  " + contract + "  " + email;
                    String indexStr = String.valueOf(index);
                    Printer.printf(indexStr + ")  " + item);
                    index++;

                }

                choice = getChoice(1, index-1);
                employeeBeanSelected = employeeBeanList.get(choice - 1);

                name = employeeBeanSelected.getName();
                surname = employeeBeanSelected.getSurname();
                contract = employeeBeanSelected.getContractType().getId();
                email = employeeBeanSelected.getEmail();

                Printer.printf("\nSelect shift time");

                for(index = 1; index <= items.length; index++ ){
                    Printer.printf(index + ")  " + items[index - 1]);
                }
                choice = getChoice(1, items.length);
                String shiftTime = items[choice - 1];

                Printer.print("Enter shift date with this format [yyyy-MM-dd]: ");
                String shiftDate = reader.readLine();

                WorkShiftBean newWorkShiftBean = new WorkShiftBean(ShiftSlots.fromString(shiftTime), shiftDate, name, surname, ContractTypes.fromString(contract), email, this.getLoggedUser().getManagerID());

                newWorkShiftBean = this.controller.insertWorkShift(newWorkShiftBean);

                if(newWorkShiftBean == null){
                    throw new ServiceException("Instatiation error");
                }

                break;

            }catch(InvalidInputException e){
                Printer.perror(e.getMessage());
            }catch(ServiceException | IOException e){
                Printer.perror(e.getMessage());
                System.exit(-1);
            }
        }

    }

}
