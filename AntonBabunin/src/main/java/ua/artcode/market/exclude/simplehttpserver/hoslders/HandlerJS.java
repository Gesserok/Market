package ua.artcode.market.exclude.simplehttpserver.hoslders;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HandlerJS implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String request = httpExchange.getRequestURI().toString();
        if ("/js/index.js".equals(request) &&
                httpExchange.getRequestMethod().equals("GET")) {
            File file = new File(
                    "\\Projects\\Java\\Gesserok\\market\\js\\index.js");
            httpExchange.sendResponseHeaders(200, 0);
            OutputStream os = httpExchange.getResponseBody();
            FileInputStream fis = new FileInputStream(file);

            while (fis.available() > 0) {
                os.write(fis.read());
            }

            fis.close();
            os.close();
        }
    }
}
