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

    private UserBean newUser;
    private RegistrationController controller;

    @Override
    public void start(UserBean loggedUser) {

       this.controller = new RegistrationController();

            this.showMenu();

            if (this.newUser.getRole() == Role.MANAGER) {

                this.managerRegistration();

            } else if (this.newUser.getRole() == Role.EMPLOYEE) {

                this.employeeRegistration();
            }

        LoginGraphicControllerCLI loginGraphicController = new LoginGraphicControllerCLI();
        loginGraphicController.authenticate();

    }



    public void showMenu(){

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Printer.printf("\nREGISTRATION");
        int choice;

        while(true) {

            try {

                Printer.printf("1) Manager Registration");
                Printer.printf("2) Employee Registration");
                Printer.printf("3) Quit");
                choice = this.getChoice(1, 3);

                if(choice == 3){
                    System.exit(1);
                }


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

                if(!(this.controller.isEmailUnique(this.newUser))){
                    throw new CredentialException("Email already Exist");
                }

                break;

            } catch (IOException | ServiceException e) {
                Printer.perror(e.getMessage());
                System.exit(-1);
            } catch(CredentialException | InvalidInputException e){
                Printer.perror(e.getMessage() + "\n");
            }

        }
    }

    public void managerRegistration() {



        try{

            this.newUser = this.controller.createManagerID(this.newUser);
            Printer.printf("This is your new managerID: " + this.newUser.getManagerID());

            this.controller.insertUser(this.newUser);

            Printer.printf("Registration Completed");


        }catch(CredentialException e){
            Printer.perror(e.getMessage() + "\n");
        }catch(ServiceException e){
            Printer.perror(e.getMessage());
            System.exit(-1);
        }



    }

    public void employeeRegistration(){

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String managerIdStr;
        UserBean managerAssociated;


        while(true) {
            try {

                Printer.print("Enter your manager ID: ");
                managerIdStr = reader.readLine();

                if (!(managerIdStr.matches("\\d+"))) {
                    throw new CredentialException("ManagerID not valid");
                }

                int managerID = Integer.parseInt(managerIdStr);
                this.newUser.setManagerID(managerID);
                managerAssociated = this.controller.checkManagerID(this.newUser);

                if(managerAssociated != null){
                    Printer.printf("Manager email: " + managerAssociated.getEmail());
                }

                this.controller.insertUser(this.newUser);

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
