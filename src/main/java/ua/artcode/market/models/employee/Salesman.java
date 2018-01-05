package ua.artcode.market.models.employee;

import ua.artcode.market.models.money.Money;

import java.util.List;

public class Salesman extends Employee {

    private transient int percent;

    public Salesman(String fullName, String login, String password,
                    Money salary) {
        super(fullName, login, password);
        this.setSalary(salary);
        this.percent = 5;
    }

    public Salesman() {
        super();
    }

    public Salesman(String login, String password) {
        super(login,password);
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    @Override
    public void setSubordinateList(List<Employee> subordinateList) {
        System.out.println("Salesman haven't the subordinates!");
    }

    @Override
    public List getSubordinateList() {
        return null;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Salesman worker = (Salesman) object;

        return this.getLogin() !=
                null ? this.getLogin().equals(worker.getLogin()) :
                worker.getLogin() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + percent;
        return result;
    }
}
