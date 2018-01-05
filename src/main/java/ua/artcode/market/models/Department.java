package ua.artcode.market.models;

import ua.artcode.market.models.employee.Employee;

import java.util.List;

public class Department {
    private List<Employee> employeeList;

    public Department(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
