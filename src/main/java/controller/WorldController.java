package main.java.controller;

import main.java.model.Board;
import main.java.model.Player;
import main.java.network.server.Server;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class WorldController {
    private final HashMap<String, Player> players;
    private final HashMap<String, Board> boards;

    public WorldController() {
        this.players = new HashMap<>();
        this.boards = new HashMap<>();
    }

    private void savePlayer(String username) {
        Player player = this.players.get(username);
        try (FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/player_saves/" + username + ".ser")) {
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(player);
        } catch (IOException e) {
            Server.LOGGER.severe("Error in saving player on file");
        }
    }

    public void restorePlayer(String username) {
        if (!this.players.containsKey(username)) {
            Player player = null;
            try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/player_saves/" + username + ".ser")) {
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
                player = (Player) inputStream.readObject();
                this.players.put(username, player);
            } catch (IOException | ClassNotFoundException e) {
                this.players.put(username, new Player (username, "test","player", 1, 1));
                this.savePlayer(username);
            }
        }
    }

    public Player getPlayer(String username) {
        return this.players.get(username);
    }

    public Boolean movePlayer(String username, char direction) {
        Player player = this.players.get(username);
        Board board = this.boards.get(player.getBoardName());
        int row = player.getRow();
        int column = player.getColumn();

        switch (direction) {
            case 'W':
                if (row > 0 && board.getTiles()[row - 1][column].isWalkable()) {
                    this.players.get(username).setRow(row - 1);
                    this.savePlayer(username);
                    return true;
                }
                else {
                    return false;
                }
            case 'S':
                if (row < board.getRows() - 1) {
                    if (board.getTiles()[row + 1][column].isWalkable()) {
                        this.players.get(username).setRow(row + 1);
                        this.savePlayer(username);
                        return true;
                    }
                }
                else {
                    return false;
                }
            case 'A':
                if (column > 0 && board.getTiles()[row][column - 1].isWalkable()) {
                    this.players.get(username).setColumn(column - 1);
                    this.savePlayer(username);
                    return true;
                }
                else {
                    return false;
                }
            case 'D':
                if (column < board.getColumns() - 1) {
                    if (board.getTiles()[row][column + 1].isWalkable()) {
                        this.players.get(username).setColumn(column + 1);
                        this.savePlayer(username);
                        return true;
                    }
                }
                else {
                    return false;
                }
            default:
                return false;
        }
    }

    public void addBoard(String username) {
        String board_name = this.players.get(username).getBoardName();
        if (!this.boards.containsKey(board_name)) {
            try {
                this.boards.put(board_name, new Board(board_name));
            } catch (FileNotFoundException e) {
                Server.LOGGER.severe("Error in loading board from file");
            }
        }
    }

    public ArrayList<Player> getBoardPlayers(String username) {
        String board_name = this.players.get(username).getBoardName();

        ArrayList<Player> board_players = new ArrayList<>();
        for (Player player : this.players.values()) {
            if (player.getBoardName().equals(board_name) && !player.getUsername().equals(username)) {
                board_players.add(player);
            }
        }
        return board_players;
    }
}
