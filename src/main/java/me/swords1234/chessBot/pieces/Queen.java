package me.swords1234.chessBot.pieces;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.Direction;
import me.swords1234.chessBot.utils.Location;
import me.swords1234.chessBot.utils.Options;
import me.swords1234.chessBot.utils.enums.Type;

public class Queen extends Peice {
    public Queen(Type type) {
        super(type);
        directionMove.add(Direction.DIAGONAL);
        directionMove.add(Direction.STRAIGHT);
    }

    @Override
    public String printType() {
        if (type == Type.WHITE) {
            return "Q";
        }
        return "q";
    }

    @Override
    protected Options allowedToMove(Location current, Location newLoc) {
        if (directionMove.contains(Direction.getDirection(current, newLoc))) {
            return new Options(true);
        }
        return new Options(false);
    }
}
