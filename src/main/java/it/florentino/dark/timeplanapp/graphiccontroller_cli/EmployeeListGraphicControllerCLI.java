package it.florentino.dark.timeplanapp.graphiccontroller_cli;

import it.florentino.dark.timeplanapp.appcontroller.EmployeeListController;
import it.florentino.dark.timeplanapp.beans.EmployeeBean;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.utils.enumaration.ContractTypes;
import it.florentino.dark.timeplanapp.utils.printer.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class EmployeeListGraphicControllerCLI extends GenericGraphicControllerCLI {

    private EmployeeListController controller;
    public void start(UserBean loggedUser){

        this.controller = new EmployeeListController();

        this.setLoggedUser(loggedUser);
        this.showMenu();

    }

    public void showMenu(){

        int choice;
        while(true) {
            Printer.printf("\n*---- Employee List ----*");
            Printer.printf("1) Insert Employee");
            Printer.printf("2) Remove Employee");
            Printer.printf("3) Back to Home");
            Printer.printf("4) Quit");

            choice = this.getChoice(1, 4);

            try {

                switch(choice) {
                    case 1 -> this.insertEmployee();
                    case 2 -> this.removeEmployee();
                    case 3 -> new ManagerHomeGraphicControllerCLI().start(this.getLoggedUser());
                    case 4 -> System.exit(1);
                    default -> throw new InvalidInputException("Invalid choice");
                }

            }catch(InvalidInputException e){
                Printer.perror(e.getMessage());
            }
        }
    }

    public void insertEmployee(){

        EmployeeBean newEmployeeBean;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int choice;

        while(true) {

            try {
                Printer.print("\nEnter Employee Name: ");
                String name = reader.readLine();

                Printer.print("Enter Employee Surname: ");
                String surname = reader.readLine();

                Printer.print("Enter Employee Email: ");
                String email = reader.readLine();

                Printer.printf("Select Contract Type");
                Printer.printf("1) Part-Time");
                Printer.printf("2) Full-Time");
                choice = getChoice(1, 2);

                switch(choice) {
                    case 1 -> newEmployeeBean = new EmployeeBean(name, surname, ContractTypes.PART_TIME, email, this.getLoggedUser().getManagerID());
                    case 2 -> newEmployeeBean = new EmployeeBean(name, surname,  ContractTypes.FULL_TIME, email, this.getLoggedUser().getManagerID());
                    default -> throw new InvalidInputException("Invalid choice");
                }

                this.controller.insertEmployee(newEmployeeBean);

                Printer.printf("Employee Added");

                break;
            }catch(IOException | ServiceException e){

                Printer.perror(e.getMessage());
                System.exit(-1);

            }catch(InvalidInputException e){

                Printer.perror(e.getMessage());
            }



        }
    }

    public void removeEmployee(){

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(true){

            try {
                Printer.print("Enter employee email: ");
                String email = reader.readLine();

                EmployeeBean employeeBeanToRemove = new EmployeeBean(email);

                this.controller.removeEmployee(employeeBeanToRemove);

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
