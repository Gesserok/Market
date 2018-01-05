package ua.artcode.market.controllers;

import ua.artcode.market.exclude.exception.NullArgumentException;
import ua.artcode.market.interfaces.IAppDb;
import ua.artcode.market.interfaces.IReport;
import ua.artcode.market.models.Bill;
import ua.artcode.market.models.Department;
import ua.artcode.market.models.employee.Employee;
import ua.artcode.market.models.employee.HeadOfSalesmen;
import ua.artcode.market.models.money.Money;

import java.time.LocalDateTime;
import java.util.List;

public class IReportImpl implements IReport {
    private IAppDb iAppDb;

    public IReportImpl(IAppDb iAppDb) {
        this.iAppDb = iAppDb;
    }


    @Override
    public Money doDepartmentReport(Department department, LocalDateTime start,
                                    LocalDateTime end)
            throws NullArgumentException {
        return doDepartmentReport(department.getEmployeeList(), 0, start, end);
    }

    @Override
    public Money calculateSalary(Employee employee, LocalDateTime start,
                                 LocalDateTime end)
            throws NullArgumentException {
        if (employee instanceof HeadOfSalesmen) {
            List<Employee> employeeList = employee.getSubordinateList();

            Money empPercent = salesPercent(employee, start, end);
            Money subPercent =
                    doSubordinateReport(0, employeeList, start, end).
                            takePercent(((HeadOfSalesmen) employee).
                                    getPercentOfPercent());

            return employee.getSalary().doSum(empPercent.doSum(subPercent));
        }
        return employee.getSalary().doSum(salesPercent(employee, start, end));

    }

    private Money doDepartmentReport(List<Employee> employeeList, int i,
                                     LocalDateTime start, LocalDateTime end)
            throws NullArgumentException {
        Money amountSalary = new Money(0,0);

        if (employeeList == null || employeeList.isEmpty() ||
                i >= employeeList.size()) return amountSalary;
        return amountSalary.doSum(calculateSalary(employeeList.get(i),
                start, end)).doSum(doDepartmentReport(employeeList,
                i + 1, start, end));
    }

    private Money salesPercent(Employee employee, LocalDateTime start,
                               LocalDateTime end)
            throws NullArgumentException {
        List<Bill> bills = iAppDb.filter(employee, null, start, end, null);
        return billsSumm(bills, 0).takePercent(employee.getPercent());
    }

    private Money billsSumm(List<Bill> bills, int i) {
        Money summ = new Money(0,0);
        if (bills == null || bills.isEmpty() || i >= bills.size()) return summ;
        return bills.get(i).getAmountPrice().doSum(billsSumm(bills, i + 1));
    }


    private Money doSubordinateReport(int i, List<Employee> employeeList,
                                      LocalDateTime start, LocalDateTime end)
            throws NullArgumentException {
        Money summ = new Money(0,0);

        if (employeeList == null || employeeList.isEmpty() ||
                i >= employeeList.size()) return summ;
        if (employeeList.get(i) instanceof HeadOfSalesmen) {

            return doSubordinateReport(0,
                    employeeList.get(i).getSubordinateList(), start,end).
                    doSum(billsSumm(iAppDb.filter(employeeList.get(i),
                            null, start, end, null), 0).
                    doSum(doSubordinateReport(i+1, employeeList, start, end)));
        }
        return billsSumm(iAppDb.filter(employeeList.get(i), null,
                start, end, null), 0).
                doSum(doSubordinateReport(i+1, employeeList, start, end));
    }

}
