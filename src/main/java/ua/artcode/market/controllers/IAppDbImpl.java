package ua.artcode.market.controllers;

import ua.artcode.market.exclude.exception.*;
import ua.artcode.market.interfaces.IAppDb;
import ua.artcode.market.models.*;
import ua.artcode.market.models.employee.Employee;
import ua.artcode.market.models.employee.Salesman;
import ua.artcode.market.models.money.Money;
import ua.artcode.market.utils.Generator;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ua.artcode.market.models.BillComparator.billComparator;

public class IAppDbImpl implements IAppDb {

    private int billNextId;
    private int productNextId;

    private List<Bill> bills;
    private List<Employee> employeeList;
    private Map<AbstractProduct,Integer> products;


    public IAppDbImpl() {
        this.employeeList = Generator.generateSalesmanList();
        this.employeeList.add(
                new Salesman("asd", "asd", "asd", new Money(123,12)));
        this.bills = new ArrayList<>();
        this.bills.add(new Bill(new Salesman()));
        this.products = new HashMap<>();
    }

//    public List<Employee> getEmployeeList() {
//        return employeeList;
//    }



    @Override
    public Map<AbstractProduct, Integer> getProducts() {
        return products;
    }

    @Override
    public List<Bill> getBills() {
        return bills;
    }

    @Override
    public List<Employee> getEmployee() {
        return employeeList;
    }

    @Override
    public Bill findBillById(int id) throws BillNotFoundException {
        return this.bills.stream().filter(billE -> billE.getId() == id).
                findFirst().orElseThrow(BillNotFoundException::new);
    }

    @Override
    public AbstractProduct findProductById(int id)
            throws ProductNotFoundException {
        return this.products.keySet().stream().
                filter(product -> product.getId() == id).findFirst().
                orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public Bill removeBill(int id) throws BillNotFoundException {
        Bill bill = findBillById(id);
        bills.remove(bill);
        return bill;
    }

    @Override
    public AbstractProduct removeProduct(int id)
            throws ProductNotFoundException {
        AbstractProduct product = findProductById(id);
        products.replace(product, products.get(product) - 1);
        return product;
    }

    @Override
    public Bill saveBill(Bill bill) throws IllegalArgumentException {
        if (bill == null) throw new IllegalArgumentException();
        bill.setId(++billNextId);

            bills.add(bill);
            return bill;
    }

    @Override
    public AbstractProduct saveProduct(AbstractProduct product)
            throws IllegalArgumentException {
        if (product == null) throw new IllegalArgumentException();
        product.setId(++productNextId);
        products.putIfAbsent(product, 1);
        return product;
    }

    @Override
    public Bill update(Bill bill)
            throws IllegalArgumentException, BillNotFoundException {
        if (bill == null) throw new IllegalArgumentException();
        int index = bills.indexOf(bill);

        if (index == -1) throw new BillNotFoundException();

        return bills.set(index, bill);
    }

    @Override
    public Employee createSalesman(String fullName, String login,
                                   String password, Money salary) {
        return new Salesman(fullName, login, password, salary);
    }

    @Override
    public Employee findSalesmanByLogin(String login)
            throws LoginOrPasswordArgumentExeption,
            LoginOrPasswordNotFoundException {
        if (login == null || login.isEmpty())
            throw new LoginOrPasswordArgumentExeption();

        if (employeeList == null || employeeList.isEmpty())
            throw new LoginOrPasswordNotFoundException();

        return employeeList.stream().filter(employee -> employee.getLogin().
                equals(login)).findFirst().
                orElseThrow(LoginOrPasswordNotFoundException::new);
    }

    @Override
    public Employee findSalesmanByToken(String userToken) throws LoginOrPasswordArgumentExeption,
            LoginOrPasswordNotFoundException {
        if (userToken == null || userToken.isEmpty())
            throw new LoginOrPasswordArgumentExeption();

        if (employeeList == null || employeeList.isEmpty())
            throw new LoginOrPasswordNotFoundException();

        return employeeList.stream().
                filter(employee -> userToken.
                        equals(employee.getToken())).findFirst().
                orElseThrow(LoginOrPasswordNotFoundException::new);
    }

    @Override
    public List<Bill> filter(Employee salesman, Product product,
                             LocalDateTime startDate, LocalDateTime endDate,
                             Comparator<Bill> billComparator)
            throws NullArgumentException {

        if (getBills() == null) return null;

        List<Bill> filtered = new ArrayList<>();
        filtered.addAll(getBills());

        if (salesman != null)
            filtered = filterBySeller(filtered, salesman);

        if (product != null)
            filtered = filterByProduct(filtered, product);

        if (startDate != null)
            filtered = filterByStartDate(filtered, startDate);

        if (endDate != null)
            filtered = filterByEndDate(filtered, endDate);

        if (billComparator != null)
            filtered.sort(billComparator.reversed());

        return filtered;
    }

    private List<Bill> filterByProduct(List<Bill> listBills,
                                       Product product)
            throws NullArgumentException{
        if (listBills == null) throw new NullArgumentException();
        if (product == null) throw new NullArgumentException();

        return listBills.stream().filter(bill ->
                bill.getProductsMap().keySet().contains(product)).
                collect(Collectors.toList());
    }

    private List<Bill> filterBySeller(List<Bill> listBills,
                                      Employee salesman)
            throws NullArgumentException{

        if (listBills == null) throw new NullArgumentException();
        if (salesman == null) throw new NullArgumentException();

        return listBills.stream().filter(bill ->
                bill.getEmployee().equals(salesman)).
                collect(Collectors.toList());
    }

    private List<Bill> filterByStartDate(List<Bill> listBills,
                                         LocalDateTime date)
            throws NullArgumentException {
        if (listBills == null) throw new NullArgumentException();
        if (date == null) return listBills;
        return listBills.stream().
                filter(bill -> date.compareTo(bill.getOpenTime()) >= 0).
                collect(Collectors.toList());
    }

    private List<Bill> filterByEndDate(List<Bill> listBills,
                                       LocalDateTime date)
            throws NullArgumentException {
        if (listBills == null ) throw new NullArgumentException();
        if (date == null) return listBills;
        return listBills.stream().
                filter(bill -> date.compareTo(bill.getCloseTime()) <= 0).
                collect(Collectors.toList());
    }

    @Override
    public Money aggrAmtPrice(Salesman salesman, LocalDateTime startDate,
                              LocalDateTime endDate)
            throws NullArgumentException {
        if (bills == null) throw new NullArgumentException();
        return aggrAmPrice(filter(salesman,null, startDate, endDate, null));
    }

    private Money aggrAmPrice(List<Bill> filteredList)
            throws NullArgumentException {
        final Money summ = new Money(0,0);
        if (filteredList == null) throw new NullArgumentException();
        if(filteredList.isEmpty()) return summ;

        filteredList.stream().map(bill -> summ.doSum(bill.getAmountPrice())).
                close();
        return summ;
    }

    @Override
    public Money averageAmountPrice(Salesman salesman, LocalDateTime startDate,
                                    LocalDateTime endDate)
            throws NullArgumentException {
        List<Bill> filteredList =
                filter(salesman,null, startDate, endDate, null);
        if (filteredList == null || filteredList.isEmpty())
            return new Money(0,0);
        return aggrAmPrice(filteredList).div(filteredList.size());
    }

    @Override
    public Bill minAmountPrice(Salesman salesman, LocalDateTime startDate,
                               LocalDateTime endDate)
            throws NullArgumentException {

        List<Bill> filtered = filter(salesman, null, startDate, endDate,
                billComparator);
        if (filtered == null || filtered.isEmpty())
            return null;
        return filtered.get(0);
    }

    @Override
    public Bill maxAmountPrice(Salesman salesman, LocalDateTime startDate,
                               LocalDateTime endDate)
            throws NullArgumentException {
        List<Bill> filtered = filter(salesman, null, startDate, endDate,
                billComparator);
        if (filtered == null || filtered.isEmpty())
            return null;
        return filtered.get(filtered.size() - 1);
    }
}
