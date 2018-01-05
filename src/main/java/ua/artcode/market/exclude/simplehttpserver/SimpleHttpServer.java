package ua.artcode.market.exclude.simplehttpserver;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import ua.artcode.market.exclude.simplehttpserver.hoslders.HandlerCSS;
import ua.artcode.market.exclude.simplehttpserver.hoslders.HandlerHolder;
import ua.artcode.market.exclude.simplehttpserver.hoslders.HandlerJS;
import ua.artcode.market.exclude.simplehttpserver.hoslders.employee.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleHttpServer implements Runnable {

    @Override
    public void run() {
        HttpServer server = null;
        try {
            HttpContext[] a = new HttpContext[7];
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/", new HandlerHolder());
            server.createContext("/login", new HandlerLoginPost());
            server.createContext("/css", new HandlerCSS());
            server.createContext("/js", new HandlerJS());

            server.createContext("/employee/bill", new HandlerBillCreateOrGet());
            server.createContext("/employee/bill/addproduct", new HandlerAddProductToBill());
            server.createContext("/employee/product", new HandlerCreateNewOrGetProduct());
//            server.createContext("/employee/getbill", new HandlerBill());
//            server.createContext("/employee/bills", new HandlerHolder.HandlerGetBills());


        } catch (IOException e) {
            e.printStackTrace();
        }






        if (server != null) {
            server.setExecutor(null);
            server.start();
        }
    }
}
