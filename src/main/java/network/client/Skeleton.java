package main.java.network.client;

import main.java.model.Player;
import main.java.network.RemoteInterface;
import main.java.
view.ClientView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Skeleton extends UnicastRemoteObject implements RemoteInterface {
    private final Client client;
    private final ClientView client_view;


    public Skeleton(Client client, ClientView client_view) throws RemoteException {
        this.client = client;
        this.client_view = client_view;
    }

    @Override
    public boolean loginPlayer(String username, String password) throws RemoteException {
        return false;
    }

    @Override
    public boolean registerPlayer(String username, String password) throws RemoteException {
        return false;
    }

    @Override
    public void connectPlayer(String username, RemoteInterface skeleton) throws RemoteException {
    }

    @Override
    public Player getPlayer(String username) {
        return null;
    }

    @Override
    public boolean movePlayer(String username, char direction) {
        return false;
    }

    @Override
    public void requireBoardPlayers(String username) throws RemoteException {
    }

    @Override
    public void viewBoardPlayers(ArrayList<Player> boardPlayers) {
        for (Player player : boardPlayers) {
            this.client_view.addOthers(player);
        }
    }
}
