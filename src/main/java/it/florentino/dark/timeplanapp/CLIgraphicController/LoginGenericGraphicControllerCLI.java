package it.florentino.dark.timeplanapp.CLIgraphicController;

import it.florentino.dark.timeplanapp.appcontroller.LoginController;
import it.florentino.dark.timeplanapp.beans.LoginBean;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.utils.printer.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginGenericGraphicControllerCLI extends GenericGraphicControllerCLI {


    public void start(){
        this.showMenu();
    }

    public void showMenu(){

        int choice;
        this.showAppName();
        Printer.printf("1) Log in now");
        Printer.printf("2) Register now");

        choice = getChoice(1, 2);
        try{
            switch(choice){
                case 1 -> this.authenticate();
                case 2 -> new RegistrationGraphicControllerCLI().start();
                default -> throw new InvalidInputException("Invalid choice");
            }
        }catch(InvalidInputException e){
            Printer.perror(e.getMessage());
        }
    }

    public void authenticate(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        LoginController controller = new LoginController();
        Printer.printf("\nLOG IN");
        try {

            Printer.print("Username: ");
            String username = reader.readLine();

            Printer.print("Password: ");
            String password = reader.readLine();

            LoginBean credentials = new LoginBean(username, password);
            UserBean loggedUser = controller.authenticate(credentials);

            switch(loggedUser.getRole()){
                case MANAGER -> Printer.printf("Manager Logged");
                case EMPLOYEE -> Printer.printf("Employee Logged");
                default -> throw new CredentialException("Invalid Credentials");
            }

        }catch(IOException | CredentialException | ServiceException e){
            Printer.perror(e.getMessage());
        }
    }

}
