package ua.artcode.simplehttpserver.hoslders.employee;

import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ua.artcode.market.exclude.exception.BillNotFoundException;
import ua.artcode.market.exclude.exception.LoginOrPasswordArgumentExeption;
import ua.artcode.market.exclude.exception.LoginOrPasswordNotFoundException;
import ua.artcode.market.exclude.exception.ProductNotFoundException;
import ua.artcode.market.json.BillJson;
import ua.artcode.market.json.ProductJson;
import ua.artcode.market.models.AbstractProduct;
import ua.artcode.market.models.Bill;
import ua.artcode.market.models.Product;
import ua.artcode.market.models.employee.Employee;
import ua.artcode.simplehttpserver.hoslders.HandlerHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class HandlerCreateNewOrGetProduct implements HttpHandler {
    public HandlerCreateNewOrGetProduct() throws IOException {
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String request = httpExchange.getRequestURI().toString();
        String response = "";



        if (httpExchange.getRequestMethod().equals("POST") &&
                request.equals("/employee/product")) {
            try {
                response = postAddProduct(httpExchange);
                httpExchange.sendResponseHeaders(200, response.length());
            } catch (LoginOrPasswordArgumentExeption e) {
                e.printStackTrace();
                response = e.toString();
                httpExchange.sendResponseHeaders(403, response.length());
            } catch (LoginOrPasswordNotFoundException e) {
                e.printStackTrace();
                response = e.toString();
                httpExchange.sendResponseHeaders(404, response.length());
            } finally {
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        }

        if (httpExchange.getRequestMethod().equals("GET") &&
                request.startsWith("/employee/product")) {
            try {
                response = getProduct(httpExchange, request);
                httpExchange.sendResponseHeaders(200, response.length());
            } catch (LoginOrPasswordArgumentExeption e) {
                e.printStackTrace();
                response = e.toString();
                httpExchange.sendResponseHeaders(403, response.length());
            } catch (LoginOrPasswordNotFoundException e) {
                e.printStackTrace();
                response = e.toString();
                httpExchange.sendResponseHeaders(404, response.length());
            } finally {
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    private String getProduct(HttpExchange httpExchange, String request) throws LoginOrPasswordNotFoundException, LoginOrPasswordArgumentExeption {
        if (!httpExchange.getRequestHeaders().containsKey("Token")) throw new LoginOrPasswordNotFoundException();
        List<String> tokenList = httpExchange.getRequestHeaders().get("Token");

        if (tokenList == null || tokenList.isEmpty()) throw new LoginOrPasswordNotFoundException();
        String userToken = tokenList.get(tokenList.size()-1);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Bill.class, new BillJson());
        HandlerHolder.getiTerminalController().findSalesmanByToken(userToken);


        int  id = Integer.parseInt(request.split("\\?")[1].
                split("=")[1]);


        AbstractProduct product = null;
        String response = "";
//        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(AbstractProduct.class, new ProductJson());
        try {
            product = HandlerHolder.getiTerminalController().getIAppDb().findProductById(id);
        } catch (ProductNotFoundException e) {
            e.printStackTrace();
        }
        if (product != null) {
            response = builder.create().toJson(product);
        }
        try {
            httpExchange.sendResponseHeaders(200, response.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private String postAddProduct(HttpExchange httpExchange)
            throws IOException, LoginOrPasswordNotFoundException, LoginOrPasswordArgumentExeption {
        if (!httpExchange.getRequestHeaders().containsKey("Token")) throw new LoginOrPasswordNotFoundException();
        List<String> tokenList = httpExchange.getRequestHeaders().get("Token");

        if (tokenList == null || tokenList.isEmpty()) throw new LoginOrPasswordNotFoundException();
        String userToken = tokenList.get(tokenList.size()-1);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Bill.class, new BillJson());
        HandlerHolder.getiTerminalController().findSalesmanByToken(userToken);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(httpExchange.getRequestBody()));
        String stringBody = null;
        try {
            stringBody = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        builder.registerTypeAdapter(AbstractProduct.class, new ProductJson());
        AbstractProduct product =
                builder.create().fromJson(stringBody, Product.class);

        String response = "";
        try {
            product = HandlerHolder.getiTerminalController().
                    getIAppDb().saveProduct(product);
            response = builder.create().toJson(product);
            httpExchange.sendResponseHeaders(200, response.length());
        } catch (IOException e) {
            e.printStackTrace();
            response = e.toString();
            httpExchange.sendResponseHeaders(404, response.length());
        }

        return response;
    }
}