package ua.artcode.market.models.employee;

import ua.artcode.market.models.money.Money;
import ua.artcode.market.utils.Generator;

import java.util.List;

public class HeadOfSalesmen extends Salesman {

    private List<Employee> subordinateList;
    private int percentOfPercent;

    public HeadOfSalesmen(String fullName, String login, String password,
                          Money salary) {
        super(fullName, login, password, salary);
        this.subordinateList = Generator.createSubordinateList();
        this.percentOfPercent = 2;
        this.setPercent(5);
    }

    public int getPercentOfPercent() {
        return percentOfPercent;
    }

//    public void setPercentOfPercent(int percentOfPercent) {
//        this.percentOfPercent = percentOfPercent;
//    }

    @Override
    public List<Employee> getSubordinateList() {
        return subordinateList;
    }

    @Override
    public void setSubordinateList(List<Employee> subordinateList) {
        this.subordinateList = subordinateList;
    }
}
