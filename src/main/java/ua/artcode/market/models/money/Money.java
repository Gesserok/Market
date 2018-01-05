package ua.artcode.market.models.money;


public class Money implements Comparable{
    private int moneyWholePart;
    private int moneyFraction;

    public Money(int moneyWholePart, int moneyFraction) {
        this.moneyWholePart = moneyWholePart;
        this.moneyFraction = moneyFraction;
        if (this.moneyFraction > 99) {
            this.moneyWholePart = moneyWholePart + moneyFraction / 100;
            this.moneyFraction = moneyFraction % 100;
        }
    }

    public int getMoneyWholePart() {
        return moneyWholePart;
    }

    public void setMoneyWholePart(int moneyWholePart) {
        this.moneyWholePart = moneyWholePart;
    }

    public int getMoneyFraction() {
        return moneyFraction;
    }

    public void setMoneyFraction(int moneyFraction) {
        this.moneyFraction = moneyFraction;
    }

    public Money doSum(Money salary) {
        if (salary == null) return this;

        return new Money(this.getMoneyWholePart() +
                salary.getMoneyWholePart() +
                ((this.getMoneyFraction() + salary.getMoneyFraction()) / 100),
                (this.getMoneyFraction() + salary.getMoneyFraction()) % 100);
    }


//    public static Money doSum(Money mon1, Money mon2) {

    //        if (mon1 == null || mon2 == null) return new Money(0,0);
    public Money takePercent (int percent) {
        int takedPercent = (this.moneyWholePart + this.moneyFraction) * percent;
        return new Money(takedPercent / 100, takedPercent % 100);
    }

    public Money multiply(Integer value) {
        if (value == 0) return new Money(0,0);
        this.setMoneyWholePart(this.moneyWholePart * value);
        this.setMoneyFraction(this.moneyFraction * value);

        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Money salary = (Money) object;

        return moneyWholePart == salary.moneyWholePart &&
                moneyFraction == salary.moneyFraction;
    }

    @Override
    public int hashCode() {
        int result = moneyWholePart;
        result = 31 * result + moneyFraction;
        return result;
    }

    @Override
    public String toString() {
        return moneyWholePart + "." + moneyFraction;
    }

    public Money div(int size) {
        return new Money(0,0);
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) return 1;

        if (this.getMoneyWholePart() > ((Money)o).getMoneyWholePart())
            return 10;

        else if (this.getMoneyWholePart() < ((Money)o).getMoneyWholePart())
            return -10;

        else if (this.getMoneyWholePart() == ((Money)o).getMoneyWholePart() &&
                this.moneyFraction > ((Money)o).getMoneyFraction()) return 5;

        else if (this.getMoneyWholePart() == ((Money)o).getMoneyWholePart() &&
                this.moneyFraction < ((Money)o).getMoneyFraction()) return -5;

        else return 0;
    }
}
