package it.florentino.dark.timeplanapp.CLIgraphicController;


import it.florentino.dark.timeplanapp.utils.printer.Printer;

import java.util.Scanner;

public abstract class GenericGraphicControllerCLI {



    public abstract void showMenu();

    public abstract void start();

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
