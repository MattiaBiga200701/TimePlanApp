package it.florentino.dark.timeplanapp.graphiccontroller_cli;


import it.florentino.dark.timeplanapp.beans.UserBean;
import it.florentino.dark.timeplanapp.utils.printer.Printer;

import java.util.Scanner;

public abstract class GenericGraphicControllerCLI {

    private UserBean loggedUser;

    public abstract void showMenu();

    public void start(UserBean loggedUser){

        this.setLoggedUser(loggedUser);
        this.showMenu();
    }

    public void setLoggedUser(UserBean loggedUser) {
        this.loggedUser = loggedUser;
    }

    public UserBean getLoggedUser() {
        return this.loggedUser;
    }

    public void showAppName(){
        Printer.printf("*---- TIME PLAN ----*\n");
    }

    public int getChoice(int firstChoice, int lastChoice){

        Scanner input = new Scanner(System.in);
        int choice;

        while(true){
            Printer.print("Please enter your choice: ");
            choice = input.nextInt();

            if(choice >= firstChoice && choice <= lastChoice){
                break;
            }else{
                Printer.perror("Invalid option");
            }
        }

        return choice;

    }

}
