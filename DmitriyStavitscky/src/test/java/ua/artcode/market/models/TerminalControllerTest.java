package ua.artcode.market.models;

import org.junit.Before;
import org.junit.Test;
import ua.artcode.market.appdb.AppDB;
import ua.artcode.market.controller.TerminalController;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TerminalControllerTest {

    private static TerminalController terminal;

    private static String name1 = "Dima Stavitscky";
    private static String name2 = "Ivan Raskolnikov";
    private static String name3 = "Kolia Ivanov";

    private static String login1 = "lDima";
    private static String login2 = "lIvan";
    private static String login3 = "lKolia";

    private static int pass1 = 123;
    private static int pass2 = 456;
    private static int pass3 = 789;

    @Before
    public void before() {

        terminal = new TerminalController(new AppDB());

        terminal.getAppDB().addProductToDataBase("Milk", 100);
        terminal.getAppDB().addProductToDataBase("Apple", 70);
        terminal.getAppDB().addProductToDataBase("Meat", 10);

        terminal.addSalesman(name1, login1, pass1, terminal.getAppDB().genId());
        terminal.addSalesman(name2, login2, pass2, terminal.getAppDB().genId());
        terminal.addSalesman(name3, login3, pass3, terminal.getAppDB().genId());

        terminal.signIn(true, login3, pass3);
        terminal.createBill();
        terminal.addProductToBill(1);
        terminal.addProductToBill(2);
        terminal.addProductToBill(3);
        terminal.closeAndSaveBill(23, 58, 59);
        terminal.logOut();

        terminal.signIn(false, name1, pass1);
        terminal.createBill();
        terminal.addProductToBill(1);
        terminal.addProductToBill(2);
        terminal.closeAndSaveBill(13, 13, 13);
        terminal.logOut();

        terminal.signIn(true, login2, pass2);
        terminal.createBill();
        terminal.addProductToBill(1);
        terminal.closeAndSaveBill(1, 1, 1);
        terminal.logOut();
    }

    @Test
    public void addSalesman() throws Exception {

        // test count of salesmans
        assertEquals(3, terminal.getCountOfSalesman());
    }

    @Test
    public void signIn() throws Exception {

        // if salesman was sign in
        terminal.signIn(true, login2, pass2);
        assertTrue(terminal.getIsLogged());
    }

    @Test
    public void closeAndSaveBill() {

        terminal.signIn(false, name1, pass1);
        terminal.createBill();
        terminal.addProductToBill(1);
        terminal.closeAndSaveBill(10, 20, 30);
        assertEquals("10:20:30", terminal.getAppDB().getBills().get(3).getTime().toString());
    }

    @Test
    public void findBillById() throws Exception {

        Bill excpectedBill = terminal.getAppDB().findBillById(7);

        assertEquals(excpectedBill, terminal.getBills().get(0));
    }

    @Test
    public void findSalesman() throws Exception {

        // salerman search
        Salesman findSalesman = terminal.getAppDB().findSalesman(login2, true);

        assertEquals(findSalesman.getFullName(), name2);
        assertEquals(findSalesman.getLogin(), login2);
        assertEquals(findSalesman.getPass(), pass2);
    }

    @Test
    public void getTopNofSalesMan() throws Exception {

        // search top salesman
        terminal.getAppDB().getSalesmans().get(0).setSumOfAllSales(150);
        terminal.getAppDB().getSalesmans().get(1).setSumOfAllSales(200);
        terminal.getAppDB().getSalesmans().get(2).setSumOfAllSales(100);

        Salesman topSalesman = terminal.getTopNofSalesMan();

        assertEquals(topSalesman.getSumOfAllSales(),
                terminal.getAppDB().getSalesmans().get(1).getSumOfAllSales(), 1);
    }

    @Test
    public void filterAndSortWitIdCompar() throws Exception {

        List <Bill> billsFilter = terminal.filterByTime(terminal.getBills(),
                new Time(0, 0, 0),
                new Time(23, 59, 59),
                new BillIdComparator());

        assertEquals(3, terminal.getCountOfBills());
    }

    @Test
    public void sortByAmountPrice() throws Exception {

        terminal.getAppDB().getBills().sort(new BillAmountPriceComparator());

        assertEquals(100, terminal.getAppDB().getBills().get(0).getAmountPrice(), 1);
        assertEquals(170, terminal.getAppDB().getBills().get(1).getAmountPrice(), 1);
        assertEquals(180, terminal.getAppDB().getBills().get(2).getAmountPrice(), 1);
    }

    @Test
    public void makeStatistics() throws Exception {
        Statistics statistics = terminal.makeStatistics();
        Statistics expStat = new Statistics(180,
                terminal.getAppDB().findSalesman(login3, true),
                100,
                terminal.getAppDB().findSalesman(login2, true),
                150,
                450);
        assertEquals(statistics, expStat);
    }

    @Test
    public void sortByProductsCount() throws Exception {

        terminal.getAppDB().getBills().sort(new BillProductsCountComparator());

        assertEquals(1, terminal.getAppDB().getBills().get(0).getProducts().size());
        assertEquals(2, terminal.getAppDB().getBills().get(1).getProducts().size());
        assertEquals(3, terminal.getAppDB().getBills().get(2).getProducts().size());
    }

    @Test
    public void sortBySalesman() throws Exception {

        terminal.getAppDB().getBills().sort(new BillSalesmanComparator());

        assertEquals(name1, terminal.getAppDB().getBills().get(0).getSalesman().getFullName());
        assertEquals(name2, terminal.getAppDB().getBills().get(1).getSalesman().getFullName());
        assertEquals(name3, terminal.getAppDB().getBills().get(2).getSalesman().getFullName());
    }

    @Test
    public void sortByTime() throws Exception {

        terminal.getAppDB().getBills().sort(new BillTimeComparator());

        assertTrue(new Time(1, 1, 1).
                compareTo(terminal.getAppDB().getBills().get(0).getTime()) == 0);

        assertTrue(new Time(13, 13, 13).
                compareTo(terminal.getAppDB().getBills().get(1).getTime()) == 0);

        assertTrue(new Time(23, 58, 59).
                compareTo(terminal.getAppDB().getBills().get(2).getTime()) == 0);
    }
}