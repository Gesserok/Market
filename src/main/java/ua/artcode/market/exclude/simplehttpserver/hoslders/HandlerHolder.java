package ua.artcode.market.exclude.simplehttpserver.hoslders;

import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ua.artcode.market.controllers.TerminalControllerFactory;
import ua.artcode.market.exclude.exception.LoginOrPasswordArgumentExeption;
import ua.artcode.market.exclude.exception.LoginOrPasswordNotFoundException;
import ua.artcode.market.interfaces.ITerminalController;
import ua.artcode.market.exclude.json.BillJson;
import ua.artcode.market.exclude.json.ProductJson;
import ua.artcode.market.models.AbstractProduct;
import ua.artcode.market.models.Bill;
import ua.artcode.market.models.Product;
import ua.artcode.market.models.employee.Employee;

import java.io.*;
import java.util.List;

public class HandlerHolder implements HttpHandler {

    private static ITerminalController iTerminalController;

    public static ITerminalController getiTerminalController() {
        return iTerminalController;
    }

    public HandlerHolder() throws IOException {
        iTerminalController = TerminalControllerFactory.create();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String request = httpExchange.getRequestURI().toString();
        if ("/".equals(request) &&
                httpExchange.getRequestMethod().equals("GET")) {
            File file = new File(
                    "\\Projects\\Market\\src\\main\\java\\ua\\artcode\\market\\view\\loginForm.html");
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            FileInputStream fis = new FileInputStream(file);
            System.out.println(request);

            while (fis.available() > 0) {
                os.write(fis.read());
            }
            fis.close();
            os.close();
        }
    }

    public static Employee token(HttpExchange httpExchange)
            throws LoginOrPasswordNotFoundException,
            LoginOrPasswordArgumentExeption {
        if (!httpExchange.getRequestHeaders().containsKey("Token"))
            throw new LoginOrPasswordNotFoundException();
        List<String> tokenList = httpExchange.getRequestHeaders().get("Token");

        if (tokenList == null || tokenList.isEmpty())
            throw new LoginOrPasswordNotFoundException();
        String userToken = tokenList.get(tokenList.size()-1);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Bill.class, new BillJson());
        return HandlerHolder.getiTerminalController().findSalesmanByToken(userToken);
    }

//    public static AbstractProduct productFromJson(HttpExchange httpExchange) {
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(httpExchange.getRequestBody()));
//        String stringBody = null;
//        try {
//            stringBody = reader.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        GsonBuilder builder = new GsonBuilder();
//        builder.registerTypeAdapter(AbstractProduct.class, new ProductJson());
//        return builder.create().fromJson(stringBody, Product.class);
//    }
}