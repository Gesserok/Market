package ua.artcode.market.exclude.json;

import com.google.gson.*;
import ua.artcode.market.models.employee.Employee;
import ua.artcode.market.models.employee.Salesman;

import java.lang.reflect.Type;


public class SalesmanJson
        implements JsonSerializer<Employee>, JsonDeserializer<Employee> {
    @Override
    public Employee deserialize(JsonElement jsonElement, Type type,
                                JsonDeserializationContext jsonDeserialContext)
            throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        String login = object.get("login").getAsString();
        String password = object.get("password").getAsString();
        return new Salesman(login, password);
    }

    @Override
    public JsonElement serialize(Employee salesman, Type type,
                                 JsonSerializationContext jsonSerialContext) {
        JsonObject object = new JsonObject();
//        object.addProperty("fullName", salesman.getFullName());
//        object.addProperty("login", salesman.getLogin());
//        object.addProperty("password", salesman.getPassword());
        object.addProperty("token", salesman.getToken());

        return object;
    }
}




