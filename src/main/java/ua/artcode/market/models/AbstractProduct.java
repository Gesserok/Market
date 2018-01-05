package ua.artcode.market.models;

import ua.artcode.market.models.money.Money;
import ua.artcode.market.utils.Generator;

import java.io.Serializable;

public abstract class AbstractProduct implements Serializable {
    private int id;
    private String name;
    private Money price;

    public AbstractProduct (String name, Money price) {
        this.setId(Generator.createProductId());
        this.setName(name);
        this.setPrice(price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    @Override
    public abstract boolean equals(Object object);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
}
