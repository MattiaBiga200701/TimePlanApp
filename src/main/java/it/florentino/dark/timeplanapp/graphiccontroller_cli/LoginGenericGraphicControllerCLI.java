package it.florentino.dark.timeplanapp.graphiccontroller_cli;

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


    public void start(UserBean loggedUser){

        this.setLoggedUser(loggedUser);
        this.showMenu();
    }

    public void showMenu(){

        int choice;
        this.showAppName();

        while(true) {
            Printer.printf("1) Log in now");
            Printer.printf("2) Register now");
            Printer.printf("3) Quit");

            choice = getChoice(1, 3);
            try {
                switch (choice) {
                    case 1 -> this.authenticate();
                    case 2 -> new RegistrationGraphicControllerCLI().start(null);
                    case 3 -> System.exit(1);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

    public void authenticate(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        LoginController controller = new LoginController();
        Printer.printf("\n*---- LOGIN ----*");

        while(true) {
            try {

                Printer.print("Email: ");
                String email = reader.readLine();

                Printer.print("Password: ");
                String password = reader.readLine();

                LoginBean credentials = new LoginBean(email, password);
                UserBean loggedUser = controller.authenticate(credentials);

                switch (loggedUser.getRole()) {
                    case MANAGER -> new ManagerHomeGraphicControllerCLI().start(loggedUser);
                    case EMPLOYEE -> Printer.printf("Employee Logged");
                    default -> throw new CredentialException("Invalid Credentials");
                }

                break;

            } catch (IOException | ServiceException e) {
                Printer.perror(e.getMessage());
                System.exit(-1);
            } catch (CredentialException e) {
                Printer.perror(e.getMessage());
            }
        }
    }

}
