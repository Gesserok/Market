package ua.artcode.market.controllers;

import ua.artcode.market.exclude.exception.*;
import ua.artcode.market.interfaces.IAppDb;
import ua.artcode.market.interfaces.ITerminalController;
import ua.artcode.market.models.AbstractProduct;
import ua.artcode.market.models.Bill;
import ua.artcode.market.models.Product;
import ua.artcode.market.models.employee.Employee;
import ua.artcode.market.models.money.Money;
import ua.artcode.market.utils.Generator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ITerminalControllerImpl implements ITerminalController {

//    private static int terminalID = 0;
    private IAppDb iAppDb;

    public ITerminalControllerImpl(IAppDb iAppDb) {
        this.iAppDb = iAppDb;
    }

    public IAppDb getIAppDb() {
        return iAppDb;
    }

    @Override
    public Employee createSalesman(String fullName, String login,
                                   String password, Money salary)
            throws IOException, LoginOrPasswordArgumentExeption, LoginOrPasswordNotFoundException {
        return iAppDb.createSalesman(fullName, login, password, salary);
    }

    @Override
    public Employee findSalesmanByLogin(String login)
            throws LoginOrPasswordArgumentExeption,
            LoginOrPasswordNotFoundException {
        return iAppDb.findSalesmanByLogin(login);
    }

    @Override
    public Employee login(String login, String password)
            throws LoginOrPasswordArgumentExeption,
            LoginOrPasswordNotFoundException {

        if (login == null || password == null ||
                login.isEmpty() || password.isEmpty())
            throw new LoginOrPasswordArgumentExeption();

        Employee employee = findSalesmanByLogin(login);

        if (!employee.getPassword().equals(password)) {
            throw new LoginOrPasswordNotFoundException();
        }
        employee.setToken(Generator.generateToken(15));
        iAppDb.getEmployee().remove(employee);
        iAppDb.getEmployee().add(employee);
        return employee;
    }

    @Override
    public Employee login(Employee employee)
            throws LoginOrPasswordArgumentExeption,
            LoginOrPasswordNotFoundException {
        return login(employee.getLogin(), employee.getPassword());
    }

    @Override
    public Employee findSalesmanByToken(String userToken)
            throws LoginOrPasswordArgumentExeption,
            LoginOrPasswordNotFoundException {
        return iAppDb.findSalesmanByToken(userToken);
    }

    @Override
    public Bill createBill(Employee employee) throws IOException {
        Bill bill = new Bill(employee);
        bill.setOpenTime(LocalDateTime.now());
        return iAppDb.saveBill(bill);
    }

    @Override
    public Bill addProduct(int billId, AbstractProduct product)
            throws IOException, BillNotFoundException {
        Bill bill = iAppDb.findBillById(billId);

        if (bill == null || product == null) return null;
        if (!bill.getProductsMap().containsKey(product)) {
            bill.getProductsMap().putIfAbsent(product, 1);
            bill.setAmountPrice(calculateAmountPrice(bill));
            iAppDb.getProducts().replace(product,
                    iAppDb.getProducts().get(product) - 1);
            return bill;
        }

        bill.getProductsMap().replace(product,
                bill.getProductsMap().get(product) + 1);
        bill.setAmountPrice(calculateAmountPrice(bill));
        iAppDb.getProducts().replace(product,
                iAppDb.getProducts().get(product) - 1);
        iAppDb.update(bill);

        return bill;
    }

    @Override
    public List<Bill> getBills() {
        return iAppDb.getBills();
    }

    @Override
    public Money calculateAmountPrice(Bill bill) {
        Money amountPrice = new Money(0,0);
        if (bill == null || bill.getProductsMap() == null ||
                bill.getProductsMap().isEmpty()) return amountPrice;
        List<Money> monies = bill.getProductsMap().keySet().stream().
                map(product -> product.getPrice().
                        multiply(bill.getProductsMap().get(product))).
                collect(Collectors.toList());

        return calculateAmountPrice(monies);

//          Code like that is easier
//        for (Map.Entry<Product, Integer> pair :
//                bill.getProductsMap().entrySet()) {
//            amountPrice = amountPrice.doSum(pair.getKey().getPrice().
//                    multiply(pair.getValue()));
//        }
    }

    private Money calculateAmountPrice(List<Money> monies) {
        final Money amountPrice = new Money(0,0);
        monies.stream().
                peek(money -> amountPrice.doSum(money));
        return amountPrice;
    }

    @Override
    public String prinBill(Bill bill) {
        return bill.toString();
    }

    @Override
    public Bill closeBill(int id) throws IOException, BillNotFoundException {
        Bill bill = iAppDb.findBillById(id);
        bill.setCloseTime(LocalDateTime.now());
        iAppDb.update(bill);
        return bill;
    }




}
