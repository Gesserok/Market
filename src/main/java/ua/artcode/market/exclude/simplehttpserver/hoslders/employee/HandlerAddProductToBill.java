package ua.artcode.market.exclude.simplehttpserver.hoslders.employee;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ua.artcode.market.exclude.exception.BillNotFoundException;
import ua.artcode.market.exclude.exception.LoginOrPasswordArgumentExeption;
import ua.artcode.market.exclude.exception.LoginOrPasswordNotFoundException;
import ua.artcode.market.exclude.exception.ProductNotFoundException;
import ua.artcode.market.models.AbstractProduct;
import ua.artcode.market.exclude.simplehttpserver.hoslders.HandlerHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class HandlerAddProductToBill implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String request = httpExchange.getRequestURI().toString();
        String response = "";

        if ("/employee/bill/addproduct".equals(request) &&
                httpExchange.getRequestMethod().equals("POST")) {
            try {
                response = postAddProductToBill(httpExchange);
                httpExchange.sendResponseHeaders(200,response.length());
            } catch (LoginOrPasswordArgumentExeption e) {
                e.printStackTrace();
                response = e.toString();
                httpExchange.sendResponseHeaders(404, response.length());
            } catch (LoginOrPasswordNotFoundException e) {
                e.printStackTrace();
                response = e.toString();
                httpExchange.sendResponseHeaders(403, response.length());
            } catch (BillNotFoundException e) {
                e.printStackTrace();
                response = e.toString();
                httpExchange.sendResponseHeaders(405, response.length());
            } catch (ProductNotFoundException e) {
                e.printStackTrace();
                response = e.toString();
                httpExchange.sendResponseHeaders(406, response.length());
            } finally {
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        }


    }

    private String postAddProductToBill(HttpExchange httpExchange)
            throws IOException, LoginOrPasswordNotFoundException,
            LoginOrPasswordArgumentExeption, ProductNotFoundException,
            BillNotFoundException {//
//        HandlerHolder.token(httpExchange);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(httpExchange.getRequestBody()));
        String line = reader.readLine();

        int billId = Integer.parseInt(line.split(",")[0].
                split(":")[1].replaceAll("\"", ""));
        int productId = Integer.parseInt(line.split(",")[1].split(":")[1].
                replaceAll("\"", "").replaceAll("}", ""));

        AbstractProduct productJson =
                HandlerHolder.getiTerminalController().
                        getIAppDb().findProductById(productId);

//        Bill bill = HandlerHolder.getiTerminalController().
// getIAppDb().findBillById(billId);
        return  HandlerHolder.getiTerminalController().
                addProduct(billId, productJson).toString();
    }

}
