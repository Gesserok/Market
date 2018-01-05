package ua.artcode.market.interfaces;

import ua.artcode.market.exclude.exception.*;
import ua.artcode.market.models.AbstractProduct;
import ua.artcode.market.models.Bill;
import ua.artcode.market.models.employee.Employee;
import ua.artcode.market.models.Product;
import ua.artcode.market.models.employee.Salesman;
import ua.artcode.market.models.money.Money;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public interface IAppDb {

    Map<AbstractProduct, Integer> getProducts();

    List<Bill> getBills();
    List<Employee> getEmployee();
    Bill findBillById(int id) throws BillNotFoundException;

    AbstractProduct findProductById(int id) throws ProductNotFoundException;

    Bill removeBill(int id) throws IOException, BillNotFoundException;

    AbstractProduct removeProduct(int id) throws ProductNotFoundException, IOException;
    Bill saveBill(Bill bill) throws IOException;

    AbstractProduct saveProduct(AbstractProduct product) throws IOException;
    Bill update(Bill bill) throws IOException, BillNotFoundException;

    Employee createSalesman(String fullName, String login, String password,
                            Money salary) throws IOException, LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException;

    Employee findSalesmanByLogin(String login) throws LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException;
    Employee findSalesmanByToken(String userToken) throws LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException;
    List<Bill> filter(Employee salesman, Product product,
                      LocalDateTime startDate, LocalDateTime endDate,
                      Comparator<Bill> billComparator) throws NullArgumentException;


    Money aggrAmtPrice(Salesman salesman, LocalDateTime startDate,
                       LocalDateTime endDate) throws NullArgumentException;

    Money averageAmountPrice(Salesman salesman, LocalDateTime startDate,
                             LocalDateTime endDate) throws NullArgumentException;

    Bill minAmountPrice(Salesman salesman, LocalDateTime startDate,
                        LocalDateTime endDate) throws NullArgumentException;

    Bill maxAmountPrice(Salesman salesman, LocalDateTime startDate,
                        LocalDateTime endDate) throws NullArgumentException;
}
