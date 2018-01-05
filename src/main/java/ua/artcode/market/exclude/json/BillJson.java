package ua.artcode.market.exclude.json;

import com.google.gson.*;
import ua.artcode.market.exclude.exception.LoginOrPasswordArgumentExeption;
import ua.artcode.market.exclude.exception.LoginOrPasswordNotFoundException;
import ua.artcode.market.models.Bill;
import ua.artcode.market.models.employee.Employee;
import ua.artcode.market.exclude.simplehttpserver.hoslders.HandlerHolder;

import java.lang.reflect.Type;

public class BillJson
        implements JsonSerializer<Bill>,
        JsonDeserializer<Bill> {
    @Override
    public Bill deserialize(JsonElement jsonElement, Type type,
                                       JsonDeserializationContext jsonDeserialContext)
            throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
//        int id = object.get("id").getAsInt();
//        JsonObject productsMap = object.get("productsMap").getAsJsonObject();
//        Product product = productsMap.get("product").getAsJsonObject();
        JsonObject employeeObj = object.get("employee").getAsJsonObject();
//        JsonObject money = object.getAsJsonObject("amountPrice").getAsJsonObject();
//        JsonObject openTime = object.getAsJsonObject("openTime").getAsJsonObject();
//        JsonObject closeTime = object.getAsJsonObject("closeTime").getAsJsonObject();


        String employeeStr = employeeObj.get("login").getAsString();
        Employee employee = null;
        try {
            employee = HandlerHolder.getiTerminalController().findSalesmanByLogin(employeeStr);
        } catch (LoginOrPasswordArgumentExeption loginOrPasswordArgumentExeption) {
            loginOrPasswordArgumentExeption.printStackTrace();
        } catch (LoginOrPasswordNotFoundException e) {
            e.printStackTrace();
        }
        return new Bill(employee);


/*        String name = object.get("name").getAsString();
        int moneyWholePart = object.get("moneyWholePart").getAsInt();
        int moneyFraction = object.get("moneyFraction").getAsInt();
        Money price = new Money(moneyWholePart, moneyFraction);
        AbstractProduct product = new Product(name, price);*/

    }

    @Override
    public JsonElement serialize(Bill bill, Type type,
                                 JsonSerializationContext jsonSerialContext) {
        Gson gson = new GsonBuilder().create();
        JsonObject object = new JsonObject();
        object.addProperty("id", gson.toJson(bill.getId()));
        object.addProperty("productsMap", gson.toJson(bill.getProductsMap()));
        object.addProperty("employee", gson.toJson(bill.getEmployee()));
        object.addProperty("amountPrice", gson.toJson(bill.getAmountPrice()));
        object.addProperty("openTime", gson.toJson(bill.getOpenTime()));
        object.addProperty("closeTime", gson.toJson(bill.getCloseTime()));

        return object;
/*        object.addProperty("id", product.getId());
        object.addProperty("name", product.getName());
        object.addProperty("moneyWholePart",
                product.getPrice().getMoneyWholePart());
        object.addProperty("moneyFraction",
                product.getPrice().getMoneyFraction());*/
    }
}