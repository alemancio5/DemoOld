package main.java.network.server;
import main.java.controller.PasswordController;
import main.java.controller.WorldController;
import main.java.model.Player;
import main.java.network.RemoteInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Stub extends UnicastRemoteObject implements RemoteInterface {
    private final Server server;
    private final WorldController world_controller;
    private final PasswordController password_controller;

    public Stub(Server server) throws RemoteException {
        this.server = server;
        this.world_controller = new WorldController();
        this.password_controller = new PasswordController();
    }

    public boolean loginPlayer(String username, String password) throws RemoteException {
        return this.password_controller.loginPlayer(username, password);
    }

    public boolean registerPlayer(String username, String password) throws RemoteException {
        return this.password_controller.registerPlayer(username, password);
    }

    @Override
    public void connectPlayer(String username, RemoteInterface skeleton) throws RemoteException {
        this.server.addSkeleton(username, skeleton);
        this.world_controller.restorePlayer(username);
        this.world_controller.addBoard(username);
        Server.LOGGER.info("Player " + username + " connected");
    }

    @Override
    public Player getPlayer(String username) {
        return this.world_controller.getPlayer(username);
    }

    @Override
    public boolean movePlayer(String username, char direction) {
        return this.world_controller.movePlayer(username, direction);
    }

    @Override
    public void requireBoardPlayers(String username) throws RemoteException {
        this.server.sendBoardPlayers(username, this.world_controller.getBoardPlayers(username));
    }

    @Override
    public void viewBoardPlayers(ArrayList<Player> boardPlayers) {
    }
}
