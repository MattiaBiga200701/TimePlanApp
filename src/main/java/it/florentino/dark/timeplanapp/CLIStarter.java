package it.florentino.dark.timeplanapp;

import it.florentino.dark.timeplanapp.graphiccontroller_cli.LoginGraphicControllerCLI;


public class CLIStarter {

    public static void main (String[] args){

        LoginGraphicControllerCLI graphicController = new LoginGraphicControllerCLI();
        graphicController.start(null);

    }
}
