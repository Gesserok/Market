package ua.artcode.market.models;

import ua.artcode.market.models.employee.Employee;
import ua.artcode.market.models.money.Money;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Bill implements Comparable<Bill> {


    private transient int id;
    private transient Map<AbstractProduct, Integer> productsMap;
    private Employee employee;
    private transient Money amountPrice;

    private transient LocalDateTime openTime;
    private transient LocalDateTime closeTime;

    public Bill(Employee employee) {
        this.setEmployee(employee);
        this.productsMap = new HashMap<>();
        this.openTime = LocalDateTime.now();
        this.setAmountPrice(new Money(0,0));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<AbstractProduct, Integer> getProductsMap() {
        return productsMap;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Money getAmountPrice() {
        return amountPrice;
    }

    public void setAmountPrice(Money amountPrice) {
        this.amountPrice = amountPrice;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Bill bill = (Bill) object;

        return id == bill.id;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "billId=" + id +
                ", productsMap=" + productsMap +
                ", employee=" + employee +
                ", amountPrice=" + amountPrice +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                '}';
    }

    @Override
    public int compareTo(Bill o) {
        if (o == null) return 1;
        return Integer.compare(0, (o.getAmountPrice().
                doSum(this.getAmountPrice().multiply(-1)).
                multiply(100)).compareTo(new Money(0, 0)));
    }
//
//    @Override
//    public int compare(Bill o1, Bill o2) {
//        return (int)((o1.getAmountPrice() - o2.getAmountPrice())*100);
//    }
//
//    @Override
//    public Comparator<Bill> thenComparing(Comparator<? super Bill> other) {
//        return new Comparator<Bill>() {
//            @Override
//            public int compare(Bill o1, Bill o2) {
//                return o1.getCloseTime().compareTo(o2.getCloseTime());
//            }
//        };
//    }
}
