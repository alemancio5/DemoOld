package main.java.view;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.model.Player;
import main.java.network.client.Client;

public class ClientView extends Application {
    static final double STAGE_ROWS = 448;
    static final double STAGE_COLUMNS = 512;
    static final double TILE_ROWS = 32;
    static final double TILE_COLUMNS = 32;
    static Client client;
    static AccessView access_view;
    static GameView game_view;

    @Override
    public void start(Stage stage) {
        // creating the client
        client = new Client("localhost", 1099, this);

        // setting the stage
        stage.setWidth(STAGE_COLUMNS);
        stage.setHeight(STAGE_ROWS);
        stage.setResizable(false);
        stage.setTitle("Demo");
        access_view = new AccessView(stage);
        stage.setScene(access_view.getScene());
        stage.show();
    }

    public void addOthers(Player player) {
        Client.LOGGER.info("Adding player " + player.getUsername() + " to the board");
        game_view.addOthers(player);
    }
}