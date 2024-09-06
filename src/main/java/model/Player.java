package main.java.model;

import java.io.Serial;
import java.io.Serializable;

public class Player implements Serializable {
    @Serial
    private final static long serialVersionUID = 3182630738296128821L;
    private final String username;
    private int row;
    private int column;
    private String skin_name;
    private final String board_name;

    public Player(String username, String board_name, String skin_name, int row, int column) {
        this.username = username;
        this.board_name = board_name;
        this.skin_name = skin_name;
        this.row = row;
        this.column = column;
    }

    public String getUsername() {
        return username;
    }

    public String getBoardName() {
        return board_name;
    }

    public String getSkinName() {
        return skin_name;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setSkinName(String player) {
        this.skin_name = player;
    }
}
