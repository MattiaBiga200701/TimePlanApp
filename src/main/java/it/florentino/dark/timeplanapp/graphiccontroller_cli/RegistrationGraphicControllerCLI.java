package it.florentino.dark.timeplanapp.graphiccontroller_cli;

import it.florentino.dark.timeplanapp.appcontroller.RegistrationController;
import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.exceptions.CredentialException;
import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.exceptions.NotUniqueEmailException;
import it.florentino.dark.timeplanapp.exceptions.ServiceException;
import it.florentino.dark.timeplanapp.utils.enumaration.Role;
import it.florentino.dark.timeplanapp.utils.printer.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class RegistrationGraphicControllerCLI extends GenericGraphicControllerCLI {

    UserBean newUser;
    public void start(UserBean loggedUser) {
        while(true) {
            this.showMenu();
            try {
                if (this.newUser.getRole() == Role.MANAGER) {

                    this.managerRegistration();

                } else if (this.newUser.getRole() == Role.EMPLOYEE) {

                    this.employeeRegistration();
                }

                break;

            } catch (NotUniqueEmailException e) {
                Printer.perror(e.getMessage() + "Retype Information\n");
            }
        }

        LoginGenericGraphicControllerCLI controller = new LoginGenericGraphicControllerCLI();
        controller.authenticate();

    }



    public void showMenu(){

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Printer.printf("\nREGISTRATION");
        int choice;

        Printer.printf("1) Manager Registration");
        Printer.printf("2) Employee Registration");
        Printer.printf("3) Quit");
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
                    case 3 -> System.exit(0);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (IOException e) {
                Printer.perror(e.getMessage());
                System.exit(-1);
            } catch(CredentialException | InvalidInputException e){
                Printer.perror(e.getMessage() + "\n");
            }

        }
    }

    public void managerRegistration() throws NotUniqueEmailException{

        RegistrationController controller = new RegistrationController();

        try{

            this.newUser = controller.createManagerID(this.newUser);
            Printer.printf("This is your new managerID: " + this.newUser.getManagerID());

            controller.insertUser(this.newUser);

            Printer.printf("Registration Completed");


        }catch(CredentialException e){
            Printer.perror(e.getMessage() + "\n");
        }catch(ServiceException e){
            Printer.perror(e.getMessage());
            System.exit(-1);
        }



    }

    public void employeeRegistration() throws NotUniqueEmailException{

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

                if(managerAssociated != null){
                    Printer.printf("Manager email: " + managerAssociated.getEmail());
                }

                controller.insertUser(this.newUser);

                Printer.printf("Registration Completed");

                break;

            } catch (IOException | ServiceException e) {
                Printer.perror(e.getMessage());
                System.exit(-1);
            } catch (CredentialException e) {
                Printer.perror(e.getMessage() + "\n");
            }
        }



    }

}
