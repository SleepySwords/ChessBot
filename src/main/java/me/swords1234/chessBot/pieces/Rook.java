package me.swords1234.chessBot.pieces;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.Direction;
import me.swords1234.chessBot.utils.Location;
import me.swords1234.chessBot.utils.Options;
import me.swords1234.chessBot.utils.enums.Type;

public class Rook extends Peice {
    public Rook(Type colorType) {
        super(colorType);
    }

    @Override
    public String printType() {
        if (type == Type.WHITE) {
            return "R";
        }
        return "r";
    }

    @Override
    protected Options allowedToMove(Location current, Location newLoc) {
        if (Direction.getDirection(current, newLoc) == Direction.STRAIGHT) {
            return new Options(true);
        }
        return new Options(false);
    }
}
