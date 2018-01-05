package ua.artcode.market.models;



import java.util.Comparator;

public class BillComparator implements Comparator<Bill> {

    public static BillComparator billComparator = new BillComparator();

    public static BillComparator other = new BillComparator();

    @Override
    public int compare(Bill o1, Bill o2) {
        return 0;
//        return (int)((o2.getAmountPrice() - o1.getAmountPrice())*1000);
    }

    @Override
    public Comparator<Bill> thenComparing(Comparator<? super Bill> other) {
        return new Comparator<Bill>() {
            @Override
            public int compare(Bill o1, Bill o2) {
                return o1.getCloseTime().compareTo(o2.getCloseTime());
            }
        };
    }


}
