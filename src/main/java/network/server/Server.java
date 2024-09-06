package main.java.network.server;

import main.java.controller.PasswordController;
import main.java.controller.WorldController;
import main.java.model.Player;
import main.java.network.RemoteInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Server {
    public static final Logger LOGGER =  Logger.getLogger(Server.class.getName());
    private Stub stub;
    private final HashMap<String, RemoteInterface> skeletons;

    public Server(int port) {
        this.skeletons = new HashMap<>();

        // creating stub
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            this.stub = new Stub(this);
            registry.bind("Stub", this.stub);
            Server.LOGGER.info("Server started");
        } catch (RemoteException | AlreadyBoundException e) {
            Server.LOGGER.severe("Server not started");
        }

        // keeping connection
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {}, 0, 1, TimeUnit.HOURS);
    }

    public void addSkeleton(String username, RemoteInterface skeleton) {
        if (!this.skeletons.containsKey(username)) {
            this.skeletons.put(username, skeleton);
        }
    }

    public void sendBoardPlayers(String username, ArrayList<Player> board_players) {
        for (Player player : board_players) {
            try {
                this.skeletons.get(username).viewBoardPlayers(board_players);
            } catch (RemoteException e) {
                Server.LOGGER.severe("Player " + player.getUsername() + " not reachable");
            }
        }
    }
}
