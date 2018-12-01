package me.swords1234.chessBot.peices;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.Direction;
import me.swords1234.chessBot.utils.Location;
import me.swords1234.chessBot.utils.Options;
import me.swords1234.chessBot.utils.Type;

public class Queen extends Peice {
    public Queen(Type type) {
        super(type);
        directionMove.add(Direction.DIAGONAL);
        directionMove.add(Direction.STRAIGHT);
    }

    @Override
    protected Options allowedToMove(Location current, Location newLoc) {
        if (directionMove.contains(Direction.getDirection(current, newLoc))) {
            return new Options(true);
        }
        return new Options(false);
    }
}
