package ua.artcode.market.controllers;

import ua.artcode.market.interfaces.ITerminalController;

import java.io.IOException;


public class TerminalControllerFactory {

    public static ITerminalController create() throws IOException {
        return new ITerminalControllerImpl(new IAppDbProxy(new IAppDbImpl()));
    }

}
