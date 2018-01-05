/*package ua.artcode.market;

import ua.artcode.market.controllers.TerminalControllerFactory;
import ua.artcode.market.interfaces.ITerminalController;

import java.io.*;
import java.util.Scanner;

import static ua.artcode.market.view.View.fRun;


public class Main {
    private ITerminalController terminalController;

    public ITerminalController getTerminalController() {
        return terminalController;
    }

    public static void main(String... args) throws IOException {
        Main main1 = new Main();
        main1.terminalController = TerminalControllerFactory.create();

        File file =
                new File("C:\\Projects\\Java\\Gesserok\\aco22\\json.txt");
        String str = "";
        Scanner scanner = new Scanner(System.in);
        if (!file.exists()) {
            fRun(scanner, main1);
        }
    }
}*/
