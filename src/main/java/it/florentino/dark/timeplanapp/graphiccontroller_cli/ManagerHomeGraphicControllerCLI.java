package it.florentino.dark.timeplanapp.graphiccontroller_cli;

import it.florentino.dark.timeplanapp.exceptions.InvalidInputException;
import it.florentino.dark.timeplanapp.utils.printer.Printer;

public class ManagerHomeGraphicControllerCLI extends GenericGraphicControllerCLI {

    public void showMenu(){

        int choice;
        while(true) {
            Printer.printf("\n*---- HOME PAGE ----*");
            Printer.printf("1) Employee List");
            Printer.printf("2) Work Schedule");
            Printer.printf("3) Schedulation View");
            Printer.printf("4) Quit");

            choice = getChoice(1, 4);
            try {

                switch (choice) {
                    case 1 -> new EmployeeListGraphicControllerCLI().start(this.getLoggedUser());
                    case 2 -> new WorkScheduleGraphicControllerCLI().start(this.getLoggedUser());
                    case 3 -> new SchedulationViewGraphicControllerCLI().start(this.getLoggedUser());
                    case 4 -> System.exit(1);
                    default -> throw new InvalidInputException("Invalid choice");
                }

                break;

            } catch (InvalidInputException e) {
                Printer.perror(e.getMessage());
            }

        }
    }
}
