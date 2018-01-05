package ua.artcode.market.exclude.json;

import com.google.gson.*;
import ua.artcode.market.models.AbstractProduct;
import ua.artcode.market.models.Product;
import ua.artcode.market.models.money.Money;

import java.lang.reflect.Type;

public class ProductJson
        implements JsonSerializer<AbstractProduct>,
        JsonDeserializer<AbstractProduct> {
    @Override
    public AbstractProduct deserialize(JsonElement jsonElement, Type type,
                                JsonDeserializationContext jsonDeserialContext)
            throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        String name = object.get("name").getAsString();
        int moneyWholePart = object.get("moneyWholePart").getAsInt();
        int moneyFraction = object.get("moneyFraction").getAsInt();
        Money price = new Money(moneyWholePart, moneyFraction);
        AbstractProduct product = new Product(name, price);

        return product;
    }

    @Override
    public JsonElement serialize(AbstractProduct product, Type type,
                                 JsonSerializationContext jsonSerialContext) {
        JsonObject object = new JsonObject();
        object.addProperty("id", product.getId());
        object.addProperty("name", product.getName());
        object.addProperty("moneyWholePart",
                product.getPrice().getMoneyWholePart());
        object.addProperty("moneyFraction",
                product.getPrice().getMoneyFraction());

        return object;
    }
}