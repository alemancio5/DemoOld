package main.java.network;

import main.java.model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteInterface extends Remote {

    boolean loginPlayer(String username, String password) throws RemoteException;

    boolean registerPlayer(String username, String password) throws RemoteException;

    void connectPlayer(String username, RemoteInterface skeleton) throws RemoteException;

    Player getPlayer(String username) throws RemoteException;

    boolean movePlayer(String username, char direction) throws RemoteException;

    void requireBoardPlayers(String username) throws RemoteException;

    void viewBoardPlayers(ArrayList<Player> boardPlayers) throws RemoteException;
}