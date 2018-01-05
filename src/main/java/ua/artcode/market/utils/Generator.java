package ua.artcode.market.utils;

import ua.artcode.market.models.employee.Employee;
import ua.artcode.market.models.Product;
import ua.artcode.market.models.employee.Salesman;
import ua.artcode.market.models.money.Money;
//import ua.artcode.market.models.money.Salary;

import java.util.*;

public class Generator {
    public static Product createProduct() {
        Product product = new Product("",new Money(0,0));
        product.setName(generateName(30));
        product.setPrice(generateProductPrice());
        return product;
    }

    private static Money generateProductPrice() {
        return new Money((int)(Math.random()*1000),  (int)(Math.random()*99));
    }

    private static String generateName(int r){
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < r; i++) {
            name.append((char) ((int) (Math.random() * 25) + 65));
        }
        return name.toString();
    }

    private static Integer random() {
        return (int)(Math.random()*300);
    }

    public static Map<Product, Integer> randomProducts(int n){
        Map<Product, Integer> products = new HashMap<>();
        for (int i = 0; i < n; i++) {
            Product p = Generator.createProduct();
            p.setId(i);
            products.put(p, Generator.random());
        }
        return products;
    }

    public static List<Employee> generateSalesmanList() {
        List<Employee> salesmanList = new ArrayList<>();
        return salesmanList;
    }

    public static List<Employee> createSubordinateList() {
        return new ArrayList<>();
    }

    public static String generateToken(int size) {
        return generateName(size).toLowerCase();
    }

    public static int createProductId() {
        return (int)(Math.random() * 1000);
    }
}
