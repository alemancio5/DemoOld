package main.java.network.client;

import main.java.model.Player;
import main.java.network.RemoteInterface;
import main.java.network.server.Server;
import main.java.view.ClientView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;


public class Client {
    public static final Logger LOGGER =  Logger.getLogger(Server.class.getName());
    private String username;
    private Skeleton skeleton;
    private RemoteInterface stub;


    public Client(String ip, int port, ClientView client_view) {
        try {
            this.skeleton = new Skeleton(this, client_view);
            Registry registry = LocateRegistry.getRegistry(ip, port);
            this.stub = (RemoteInterface) registry.lookup("Stub");
        } catch (RemoteException | NotBoundException e) {
            Client.LOGGER.severe("Server not reachable");
        }
    }

    public Boolean registerPlayer(String username, String password) {
        try {
            if (stub.registerPlayer(username, password)) {
                this.username = username;
                return true;
            }
        } catch (RemoteException e) {
            Client.LOGGER.severe("Server not reachable");
        }
        return false;
    }

    public boolean loginPlayer(String username, String password) {
        try {
            if (stub.loginPlayer(username, password)) {
                this.username = username;
                return true;
            }
        } catch (RemoteException e) {
            Client.LOGGER.severe("Server not reachable");
        }
        return false;
    }

    public void connectPlayer() {
        try {
            stub.connectPlayer(this.username, this.skeleton);
        } catch (RemoteException e) {
            Client.LOGGER.severe("Server not reachable");
        }
    }

    public void requireBoardPlayers() {
        try {
            stub.requireBoardPlayers(this.username);
        } catch (RemoteException e) {
            Client.LOGGER.severe("Server not reachable");
        }
    }

    public Player getPlayer() {
        try {
            return stub.getPlayer(this.username);
        } catch (RemoteException e) {
            Client.LOGGER.severe("Server not reachable");
        }
        return null;
    }

    public Boolean movePlayer(char direction) {
        try {
            return stub.movePlayer(this.username, direction);
        } catch (RemoteException e) {
            Client.LOGGER.severe("Server not reachable");
        }
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
