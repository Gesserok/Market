package ua.artcode.market.models.employee;

import ua.artcode.market.models.money.Money;
import ua.artcode.market.utils.Generator;

import java.util.List;

public abstract class Employee implements Comparable<Salesman> {
    private transient String fullName;
    private String login;
    private String password;
    private String token;
    private transient Money salary;
    private transient int percent;


    public Employee() {
    }

    public Employee(String fullName, String login, String password) {
        this.fullName = fullName;
        this.login = login;
        this.password = password;
    }

    public Employee(String login, String password) {
        this.fullName = "";
        this.login = login;
        this.password = password;
        this.token = null;
        this.salary = new Money(0,0);
        this.percent = 0;
    }

    public void setConnected() {
        token = Generator.generateToken(15);
    }

    public String getToken() {
        return token;
    }

    public Money getSalary() {
        return salary;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void setSalary(Money salary) {
        this.salary = salary;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract void setSubordinateList(List<Employee> subordinateList);

    public abstract List<Employee> getSubordinateList();

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "fullName='" + fullName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int compareTo(Salesman o) {
        return this.getLogin().compareTo(o.getLogin());
    }

    public void setToken(String token) {
        this.token = token;
    }
}
