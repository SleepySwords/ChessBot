package me.swords1234.chessBot;

import me.swords1234.chessBot.utils.Direction;
import me.swords1234.chessBot.utils.Location;
import me.swords1234.chessBot.utils.Options;
import me.swords1234.chessBot.utils.Type;

import java.util.ArrayList;
import java.util.List;

public abstract class Peice {
    protected Type type;
    protected List<Direction> directionMove;

    public Peice(Type colorType) {
        directionMove = new ArrayList<>();
        type = colorType;
    }

    public Type getType() {
        return type;
    }

    protected abstract Options allowedToMove(Location current, Location newLoc);
}
