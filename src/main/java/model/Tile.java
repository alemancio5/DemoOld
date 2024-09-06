package main.java.model;

public class Tile {
    private final TileType type;

    public Tile(char type) {
        this.type = fromChar(type);
    }

    private TileType fromChar(char type) {
        return switch (type) {
            case 'e' -> TileType.EMPTY;
            case 'w' -> TileType.WALL;
            default -> null;
        };
    }

    public boolean isWalkable() {
        return this.type != TileType.WALL;
    }

    public TileType getType() {
        return type;
    }
}

