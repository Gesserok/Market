package ua.artcode.market.interfaces;

import ua.artcode.market.exclude.exception.*;
import ua.artcode.market.models.AbstractProduct;
import ua.artcode.market.models.employee.Employee;
import ua.artcode.market.models.Bill;
import ua.artcode.market.models.Product;
import ua.artcode.market.models.money.Money;

import java.io.IOException;
import java.util.List;

public interface ITerminalController {

    Bill createBill(Employee employee) throws IOException;

    Bill addProduct(int billId, AbstractProduct product) throws IOException, BillNotFoundException;

    List<Bill> getBills();

    Money calculateAmountPrice(Bill bill);

    String prinBill(Bill bill);

    Bill closeBill(int id) throws IOException, BillNotFoundException;

    IAppDb getIAppDb();



    Employee createSalesman(String fullName, String login, String password,
                            Money salary) throws IOException, LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException;

    Employee findSalesmanByLogin(String login) throws LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException;
    Employee login (String login, String password) throws LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException;
    Employee login (Employee employee) throws LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException;

    Employee findSalesmanByToken(String userToken) throws LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException;
}
