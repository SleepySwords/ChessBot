package me.swords1234.chessBot.peices;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.Direction;
import me.swords1234.chessBot.utils.Location;
import me.swords1234.chessBot.utils.Options;
import me.swords1234.chessBot.utils.Type;

public class Bishop extends Peice {
    public Bishop(Type colorType) {
        super(colorType);
    }

    @Override
    public String printType() {
        if (type == Type.WHITE) return "B";
        return "b";
    }

    @Override
    protected Options allowedToMove(Location current, Location newLoc) {
        if (Direction.getDirection(current, newLoc) == Direction.DIAGONAL) {
            return new Options(true);
        }
        return new Options(false);
    }
}
