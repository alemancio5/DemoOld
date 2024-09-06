package main.java.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {

    private final String board_name;
    private final int rows;
    private final int columns;
    private final Tile[][] tiles;

    public Board(String board_name) throws FileNotFoundException {
        this.board_name = board_name;

        // initializing from file
        File file = new File("src/main/resources/board_files/" + board_name + ".txt");
        Scanner scanner = new Scanner(file);
        this.rows = scanner.nextInt();
        this.columns = scanner.nextInt();
        this.tiles = new Tile[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.tiles[i][j] = new Tile(scanner.next().charAt(0));
            }
        }
        scanner.close();
    }

    public void print(Player player) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                if (player.getRow() == i && player.getColumn() == j) {
                    System.out.print("PLAYER\t");
                }
                else {
                    System.out.print(this.getTiles()[i][j].getType().toString() + "\t");
                }
            }
            System.out.println();
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}


