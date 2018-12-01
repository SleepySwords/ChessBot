package me.swords1234.chessBot;

import me.swords1234.chessBot.utils.Direction;
import me.swords1234.chessBot.utils.Location;

public abstract class Peice {
    private Type type;
    private Location location = new Location(3,44);
    private Direction[] directionMove;

    public Location getLocation() {
        return location;
    }
}
enum Type {
    BLACK,
    WHITE
}
