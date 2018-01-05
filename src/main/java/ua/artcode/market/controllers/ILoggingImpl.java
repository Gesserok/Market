package ua.artcode.market.controllers;

import ua.artcode.market.exclude.exception.LoginOrPasswordArgumentExeption;
import ua.artcode.market.exclude.exception.LoginOrPasswordNotFoundException;
import ua.artcode.market.interfaces.IAppDb;
import ua.artcode.market.interfaces.ILogging;
import ua.artcode.market.models.employee.Employee;
import ua.artcode.market.models.employee.Salesman;
import ua.artcode.market.models.money.Money;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ILoggingImpl implements ILogging {

    private static volatile ILogging instance;
    private IAppDb iAppDb;

    private ILoggingImpl(IAppDb iAppDb) throws IOException {
        this.iAppDb = iAppDb;
        /*for (Employee salesman : iAppDb.getEmployee()){
            write(String.format("Fullname: %s, Login: %s, Password: %s, \r\n",
                    salesman.getFullName(), salesman.getLogin(),
                    salesman.getPassword()));
        }*/
    }

    public static ILogging getInstance(IAppDb iAppDb) throws IOException {
        if (instance == null) {
            synchronized (ILoggingImpl.class) {
                if (instance == null) {
                    instance = new ILoggingImpl(iAppDb);
                }
            }
        }
        return instance;
    }

    @Override
    public void write(String messege)
            throws IOException {
        File file = new File("log.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file,true);
        fileWriter.write(messege);
        fileWriter.flush();
        fileWriter.close();
    }


    @Override
    public Employee createSalesman(String fullName, String login,
                                   String password, Money salary) throws LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException {
        Employee salesman = null;
        Employee seller = findSalesmanByLogin(login);
        if (seller == null) {
            salesman = new Salesman(fullName, login, password,
                    new Money(123,12));
            iAppDb.getEmployee().add(salesman);
        }
        return salesman;
    }

    @Override
    public Employee login(String login, String password) {
/*        Employee seller = findSalesmanByLogin(login);
        if (seller == null) {
            return null;
        }
        if (!password.equals(seller.getPassword())) {
            return null;
        }
        seller.setIsConnected(true);
        return seller;*/
        return null;
    }

    @Override
    public Salesman logout(Salesman salesman) {
/*        salesman.setIsConnected(false);
        return salesman;*/
        return null;
    }

    @Override
    public Employee findSalesmanByLogin(String login) throws LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException {
        /*
        if (salesmenList == null || salesmenList.isEmpty())
            throw new LoginOrPasswordNotFoundException();


        for (Employee salesman : salesmenList) {
            if (login.equals(salesman.getLogin())) {
                return salesman;
            }
        }*/
        return iAppDb.findSalesmanByLogin(login);
    }

    @Override
    public Employee findSalesmanByToken(String userToken) throws LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException {
        return iAppDb.findSalesmanByToken(userToken);
    }
}
