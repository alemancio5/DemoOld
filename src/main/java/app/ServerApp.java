package main.java.app;

import main.java.network.server.Server;

public class ServerApp {
    public static void main(String[] args) {
        Server server = new Server(1099);
    }
}
