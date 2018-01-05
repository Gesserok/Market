package ua.artcode.market.exclude.simplehttpserver.hoslders.employee;

import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ua.artcode.market.exclude.exception.*;
import ua.artcode.market.exclude.json.ProductJson;
import ua.artcode.market.models.AbstractProduct;
import ua.artcode.market.models.Product;
import ua.artcode.market.exclude.simplehttpserver.hoslders.HandlerHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class HandlerCreateNewOrGetProduct implements HttpHandler {
    public HandlerCreateNewOrGetProduct() throws IOException {
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String request = httpExchange.getRequestURI().toString();
        String response = "";
        System.out.println(request);

        if (httpExchange.getRequestMethod().equals("POST") &&
                "/employee/product".equals(request)) {
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

        if (request.startsWith("/employee/product") &&
                httpExchange.getRequestMethod().equals("GET")) {

            System.out.println("OKOKOKOK");

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
                System.out.println(response);
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    private String getProduct(HttpExchange httpExchange, String request)
            throws LoginOrPasswordNotFoundException, LoginOrPasswordArgumentExeption{

//        HandlerHolder.token(httpExchange);

        int  id = Integer.parseInt(request.split("\\?")[1].
                split("=")[1]);


        AbstractProduct product = null;
        String response = "";
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(AbstractProduct.class, new ProductJson());

        try {
            product = HandlerHolder.getiTerminalController().getIAppDb().findProductById(id);
        } catch (ProductNotFoundException e) {
            e.printStackTrace();
            response = e.toString();
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

//        HandlerHolder.token(httpExchange);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(httpExchange.getRequestBody()));
        String stringBody = null;
        try {
            stringBody = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(stringBody);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(AbstractProduct.class, new ProductJson());
        AbstractProduct product =
                builder.create().fromJson(stringBody, Product.class);

        System.out.println(product.getId() + " " + product.getName() + " " + product.getPrice());

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
