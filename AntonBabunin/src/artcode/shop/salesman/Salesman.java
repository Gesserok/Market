package artcode.shop.salesman;

import artcode.shop.bill.Bill;
import artcode.shop.product.Product;
import artcode.shop.terminal.Terminal;

import java.util.Scanner;

public class Salesman {
    private String fullName;
    private String login;
    private String password;

    private Salesman() {

    }

    public Salesman(String fullName, String login, String password) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean equals (Salesman salesman) {
        if (salesman != null) {
            return this.fullName.equals(salesman.fullName);
        }
        return false;
    }

    public Bill createBill (Terminal terminal) { // Salesman create a new bill at terminal
        if (terminal != null && terminal.login(this))  {
            return terminal.createBill(this);
        } else return null;



//        return  (terminal != null && terminal.login(this)) ? terminal.createBill(this)  : null;
    }


    public void login (Terminal terminal) {
        if (terminal != null) {
            Scanner scanner = new Scanner(System.in);
            String login = "";
            do {
                System.out.println("Enter your login:");
                login = scanner.nextLine();
            } while (login.isEmpty());
            String password = "";
            do {
                System.out.println("Enter your password:");
                password = scanner.nextLine();
            } while (password.isEmpty());

            if (this.getLogin().equals(login) && this.getPassword().equals(password)) {
                terminal.login(this);
            }
        }
    }
    public boolean loginAutomatic (Terminal terminal) {
        String login = this.login;
        String password = this.password;

            if (login.equals(this.getLogin()) && password.equals(this.getPassword())) {
                boolean bool1 = terminal.login(this);
                return bool1;
            }
        return false;
    }

// --------------------------------------------------------------------------------------------
    public boolean addProduct (Terminal terminal, Product product) {
        if (terminal != null && product != null) {
            Bill bill = terminal.addProduct(product);
            bill.addProduct(product);
            return true;
        }
        return false;
    }




    public void setLogin(String login) {
        this.login = login;
    }



    public void setPassword(String password) {
        this.password = password;
    }
}
