package ua.artcode.market.exclude;

import ua.artcode.market.exclude.simplehttpserver.SimpleHttpServer;

public class ServerStart {

    public static void main(String[] args) {
        new SimpleHttpServer().run();
    }
}
