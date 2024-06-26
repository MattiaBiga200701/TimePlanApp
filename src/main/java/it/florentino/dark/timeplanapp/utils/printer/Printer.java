package it.florentino.dark.timeplanapp.utils.printer;

public class Printer {

        private Printer () {}

        public static void printf(String s) {
            printCLI(String.format("%s%n", s));
        }

        public static void print(String s) {
            printCLI(s);
        }

        public static void perror(String s){
            printCLI("\033[31m" + s + "\033[0m");
        }

        private static void printCLI(String s) {
            System.out.print(s);
        }

}
