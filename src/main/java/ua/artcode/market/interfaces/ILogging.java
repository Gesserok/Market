package ua.artcode.market.interfaces;

import ua.artcode.market.exclude.exception.LoginOrPasswordArgumentExeption;
import ua.artcode.market.exclude.exception.LoginOrPasswordNotFoundException;
import ua.artcode.market.models.employee.Employee;
import ua.artcode.market.models.employee.Salesman;
import ua.artcode.market.models.money.Money;

import java.io.IOException;
import java.util.List;

public interface ILogging {

    void write (String messege) throws IOException;

    Employee createSalesman(String fullName, String login, String password,
                            Money salary) throws IOException, LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException;
    Employee login(String login, String password) throws IOException;
    Salesman logout(Salesman salesman) throws IOException;
    Employee findSalesmanByLogin(String login) throws LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException;

    Employee findSalesmanByToken(String userToken) throws LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException;
}
