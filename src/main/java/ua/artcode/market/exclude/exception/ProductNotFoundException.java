package ua.artcode.market.exclude.exception;


import java.util.function.Supplier;

public class ProductNotFoundException extends MarketAppException
        implements Supplier<ProductNotFoundException> {

    @Override
    public ProductNotFoundException get() {
        return new ProductNotFoundException();
    }
}
