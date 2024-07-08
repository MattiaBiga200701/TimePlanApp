package it.florentino.dark.timeplanapp;

import it.florentino.dark.timeplanapp.graphiccontroller_cli.LoginGenericGraphicControllerCLI;


public class CLIStarter {

    public static void main (String[] args){

        LoginGenericGraphicControllerCLI graphicController = new LoginGenericGraphicControllerCLI();
        graphicController.start();

    }
}
