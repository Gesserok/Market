package ua.artcode.market.models;

import ua.artcode.market.models.money.Money;

public class Product extends AbstractProduct {

    public Product (String name, Money price) {
        super(name,price);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        AbstractProduct that = (AbstractProduct) object;

        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        int result;
        result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "AbstractProduct{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", price=" + this.getPrice() +
                '}';
    }

}
