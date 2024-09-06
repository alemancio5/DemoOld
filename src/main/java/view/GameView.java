package main.java.view;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.java.model.Player;

import java.util.Objects;

import static main.java.view.ClientView.*;

public class GameView {
    private final Group group;
    private Scene scene;
    private ImageView board;
    private ImageView player;
    private Group others;

    public GameView(Stage stage) {
        // create the group
        this.group = new Group();
        this.others = new Group();
        this.group.getChildren().add(this.others);

        // create the scene
        this.scene = new Scene(group, STAGE_COLUMNS, STAGE_ROWS);

        // add the board
        this.addBoard();

        // add the player
        this.addPlayer();

        // scene event handler
        this.scene.setOnKeyPressed(event -> {
            new Thread(() -> {
                double columns = 0;
                double rows = 0;
                boolean moved = false;
                switch (event.getCode()) {
                    case W:
                        moved = client.movePlayer('W');
                        rows = TILE_ROWS;
                        break;
                    case A:
                        moved = client.movePlayer('A');
                        columns = TILE_COLUMNS;
                        break;
                    case S:
                        moved = client.movePlayer('S');
                        rows = -TILE_ROWS;
                        break;
                    case D:
                        moved = client.movePlayer('D');
                        columns = -TILE_COLUMNS;
                        break;
                    default:
                        break;
                }
                if (moved) {
                    double final_columns = columns;
                    double final_rows = rows;
                    Platform.runLater(() -> {
                        moveBoard(final_columns, final_rows);
                        moveOthers(final_columns, final_rows);
                    });
                }
            }).start();
        });
    }

    public Scene getScene() {
        return this.scene;
    }

    public void addBoard() {
        Image board_image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/main/resources/board_images/" + client.getPlayer().getBoardName() + ".png")));
        this.board = new ImageView(board_image);
        this.board.setX((STAGE_COLUMNS / 2) - (client.getPlayer().getColumn() * TILE_COLUMNS) - (TILE_COLUMNS / 2));
        this.board.setY((STAGE_ROWS / 2) - (client.getPlayer().getRow() * TILE_ROWS) - (TILE_ROWS / 2));
        this.group.getChildren().add(this.board);
    }

    private void moveBoard(double columns, double rows) {
        this.board.setX(this.board.getX() + columns);
        this.board.setY(this.board.getY() + rows);
    }

    public void addPlayer() {
        Image player_image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/main/resources/skin_images/" + client.getPlayer().getSkinName() + ".png")));
        this.player = new ImageView(player_image);
        this.player.setX((STAGE_COLUMNS / 2) - (TILE_COLUMNS / 2));
        this.player.setY((STAGE_ROWS / 2) - (3 * TILE_ROWS / 2));
        this.group.getChildren().add(this.player);
    }

    public void addOthers(Player player) {
        Image other_image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/main/resources/skin_images/" + player.getSkinName() + ".png")));
        ImageView other = new ImageView(other_image);
        other.setX((STAGE_COLUMNS / 2) - (client.getPlayer().getColumn() * TILE_COLUMNS) + (player.getColumn() * TILE_COLUMNS) - (TILE_COLUMNS / 2));
        other.setY((STAGE_ROWS / 2) - (client.getPlayer().getRow() * TILE_ROWS) + (player.getRow() * TILE_ROWS) - (TILE_ROWS / 2));
        this.group.getChildren().remove(this.others);
        this.others.getChildren().add(other);
        this.group.getChildren().add(this.others);
    }

    private void moveOthers(double columns, double rows) {
        this.others.setLayoutX(this.others.getLayoutX() + columns);
        this.others.setLayoutY(this.others.getLayoutY() + rows);
    }
}
