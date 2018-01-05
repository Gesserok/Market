package ua.artcode.market.interfaces;

import ua.artcode.market.exclude.exception.NullArgumentException;
import ua.artcode.market.models.Department;
import ua.artcode.market.models.employee.Employee;
import ua.artcode.market.models.money.Money;

import java.time.LocalDateTime;

public interface IReport {

    Money doDepartmentReport (Department department, LocalDateTime start,
                              LocalDateTime end) throws NullArgumentException;

    Money calculateSalary(Employee employee, LocalDateTime start,
                          LocalDateTime end) throws NullArgumentException;
}
