package ua.artcode.market.controllers;

import ua.artcode.market.exclude.exception.*;
import ua.artcode.market.interfaces.IAppDb;
import ua.artcode.market.interfaces.ILogging;
import ua.artcode.market.models.AbstractProduct;
import ua.artcode.market.models.Bill;
import ua.artcode.market.models.employee.Employee;
import ua.artcode.market.models.Product;
import ua.artcode.market.models.employee.Salesman;
import ua.artcode.market.models.money.Money;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class IAppDbProxy implements IAppDb, ILogging{

    private IAppDb iAppDb;
    private ILogging iLogging;

    public IAppDbProxy(IAppDb iAppDb) throws IOException {
        this.iAppDb = iAppDb;
        this.iLogging = ILoggingImpl.getInstance(iAppDb);
    }

    @Override
    public void write(String message) throws IOException {
        iLogging.write(message);
    }

    @Override
    public Map<AbstractProduct, Integer> getProducts() {
        return iAppDb.getProducts();
    }

    @Override
    public List<Bill> getBills() {
        return iAppDb.getBills();
    }

    @Override
    public List<Employee> getEmployee() {
        return iAppDb.getEmployee();
    }



    @Override
    public Bill findBillById(int id) throws BillNotFoundException {
        return iAppDb.findBillById(id);
    }

    @Override
    public AbstractProduct findProductById(int id) throws ProductNotFoundException {
        return iAppDb.findProductById(id);
    }

    @Override
    public Employee findSalesmanByLogin(String login) throws LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException {
        return iLogging.findSalesmanByLogin(login);
    }

    @Override
    public Employee findSalesmanByToken(String userToken) throws LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException {
        return iLogging.findSalesmanByToken(userToken);
    }

    @Override
    public List<Bill> filter(Employee salesman, Product product,
                            LocalDateTime startDate, LocalDateTime endDate,
                            Comparator<Bill> billComparator)
            throws NullArgumentException {
        return iAppDb.filter(salesman, product, startDate, endDate,
                billComparator);
    }

    @Override
    public Money aggrAmtPrice(Salesman salesman, LocalDateTime startDate,
                              LocalDateTime endDate)
            throws NullArgumentException {
        return iAppDb.aggrAmtPrice(salesman, startDate,endDate);
    }

    @Override
    public Money averageAmountPrice(Salesman salesman, LocalDateTime startDate,
                                    LocalDateTime endDate)
            throws NullArgumentException {
        return iAppDb.averageAmountPrice(salesman, startDate, endDate);
    }

    @Override
    public Bill minAmountPrice(Salesman salesman, LocalDateTime startDate,
                               LocalDateTime endDate)
            throws NullArgumentException {
        return iAppDb.minAmountPrice(salesman, startDate, endDate);
    }

    @Override
    public Bill maxAmountPrice(Salesman salesman, LocalDateTime startDate,
                               LocalDateTime endDate)
            throws NullArgumentException {
        return iAppDb.maxAmountPrice(salesman, startDate, endDate);
    }

    @Override
    public Bill removeBill(int id) throws IOException, BillNotFoundException {
        Bill bill;
        String message;

        try {
            bill = iAppDb.removeBill(id);
        } catch (BillNotFoundException e) {
            message = String.format("Bill id=%d not found \r\n", id);
            iLogging.write(message);
            throw e;
        }
        message = String.format("Bill %s removed \r\n", bill);
        iLogging.write(message);
        return bill;
    }

    @Override
    public AbstractProduct removeProduct(int id) throws IOException, ProductNotFoundException {
        String message = null;
        AbstractProduct product1;
        try {
            product1 = iAppDb.removeProduct(id);
        } catch (ProductNotFoundException e) {
            message = String.format("Product id=%s wasn't removed \r\n", id);
            iLogging.write(message);
            throw e;
        }
        message = String.format("Product %s saved \r\n", product1);
        iLogging.write(message);
        return product1;
    }

    @Override
    public Bill saveBill(Bill bill) throws IOException {
        Bill bill1 = iAppDb.saveBill(bill);
        String message = null;
        if (bill1 == null) {
            message = String.format("Bill %s wasn't saved \r\n", bill1);
            iLogging.write(message);
            return null;
        }
        message = String.format("Bill %s saved \r\n", bill1);
        iLogging.write(message);
        return bill1;
    }

    @Override
    public AbstractProduct saveProduct(AbstractProduct product) throws IOException {
        AbstractProduct product1 = iAppDb.saveProduct(product);
        String message = null;
        if (product1 == null) {
            message = String.format("Product %s wasn't saved \r\n", product1);
            iLogging.write(message);
            return null;
        }
        message = String.format("Product %s saved \r\n", product1);
        iLogging.write(message);
        return product1;
    }

    @Override
    public final Bill update(Bill bill) throws IOException, BillNotFoundException {
        Bill found = iAppDb.update(bill);
        String message = null;
        if (bill.getCloseTime() != null) {
            message = String.format("Bill %s is closed ad it can't be " +
                            "updated \r\n", bill);
            iLogging.write(message);
            return null;
        }
        if (found == null){
            message = String.format("Bill %s not found \r\n", bill);
            iLogging.write(message);
            return found;
        }
        message = String.format("Bill %s was updated \r\n", bill);
        iLogging.write(message);
        return found;
    }

    @Override
    public Employee createSalesman(String fullName, String login,
                                   String password, Money salary)
            throws IOException, LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException {
        boolean result = false;
        Employee salesman = iLogging.createSalesman(fullName, login, password, salary);
        if (salesman != null) {
            result = true;
        }
        iLogging.write(String.format("Employee was created: " +
                        "FullName %s login %s and password %s, result %s\r\n",
                fullName, login, password, result));

        return salesman;
    }

    @Override
    public Employee login(String login, String password) throws IOException {

        boolean result = false;
        Employee salesman = iLogging.login(login, password);
        if (salesman != null) {
            result = true;
        }
        iLogging.write(String.format("Employee try connect with login %s " +
                        "and password %s," + "result %s\r\n",
                        login, password, result));
        return salesman;
    }

    @Override
    public Salesman logout(Salesman salesman) throws IOException {
        boolean result = false;
        Salesman salesman1 = iLogging.logout(salesman);
        if (salesman1 != null) {
            result = true;
        }
        iLogging.write(String.format("Employee was logout with login %s " +
                        "and password %s," + "result %s\r\n",
                salesman1.getLogin(), salesman1.getPassword(), result));
        return salesman1;
    }


}
