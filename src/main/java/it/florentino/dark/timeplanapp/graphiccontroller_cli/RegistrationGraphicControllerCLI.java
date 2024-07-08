package it.florentino.dark.timeplanapp.graphiccontroller_cli;

import it.florentino.dark.timeplanapp.appcontroller.RegistrationController;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;
import it.florentino.dark.timeplanapp.utils.printer.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class RegistrationGraphicControllerCLI extends GenericGraphicControllerCLI {

    UserBean newUser;
    public void start(){

        this.showMenu();
        switch(this.newUser.getRole()){
            case MANAGER -> this.managerRegistration();
            case EMPLOYEE -> this.employeeRegistration();
        }

    }

    public void showMenu(){

        int choice;
        this.showAppName();
        Printer.printf("1) Register now");
        Printer.printf("2) Log in now");

        choice = getChoice(1, 2);
        try{
            switch(choice){
                case 1 -> this.registrationChoice();
                case 2 -> new LoginGenericGraphicControllerCLI().start();
                default -> throw new InvalidInputException("Invalid choice");
            }

        }catch(InvalidInputException e){
            Printer.perror(e.getMessage());
        }
    }

    public void registrationChoice(){

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Printer.printf("\nREGISTRATION");
        int choice;

        Printer.printf("1) Manager Registration");
        Printer.printf("2) Employee Registration");
        choice = this.getChoice(1, 2);

        while(true) {

            try {

                Printer.print("Username: ");
                String username = reader.readLine();

                Printer.print("Email: ");
                String email = reader.readLine();


                Printer.print("Password: ");
                String password = reader.readLine();

                Printer.print("Confirm password: ");
                String retypedPassword = reader.readLine();

                if (!(password.equals(retypedPassword))) throw new CredentialException("Password do not match");

                switch(choice){
                    case 1 -> this.newUser = new UserBean(username, email, password, Role.MANAGER);
                    case 2 -> this.newUser = new UserBean(username, email, password, Role.EMPLOYEE);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (IOException e) {
                Printer.perror(e.getMessage());
                System.exit(-1);
            } catch(CredentialException | InvalidInputException e){
                Printer.perror(e.getMessage());
            }

        }
    }

    public void managerRegistration(){

    }

    public void employeeRegistration(){

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String managerIdStr;
        UserBean managerAssociated;
        RegistrationController controller = new RegistrationController();

        while(true) {
            try {

                Printer.print("Enter your manager ID: ");
                managerIdStr = reader.readLine();

                if (!(managerIdStr.matches("\\d+"))) {
                    throw new CredentialException("ManagerID not valid");
                }

                int managerID = Integer.parseInt(managerIdStr);
                this.newUser.setManagerID(managerID);
                managerAssociated = controller.checkManagerID(this.newUser);

                break;

            } catch (IOException | ServiceException e) {
                Printer.perror(e.getMessage());
                System.exit(-1);
            } catch (CredentialException e) {
                Printer.perror(e.getMessage());
            }
        }

        if(managerAssociated != null){
            Printer.printf("Manager email: " + managerAssociated.getEmail());
        }
    }
}
