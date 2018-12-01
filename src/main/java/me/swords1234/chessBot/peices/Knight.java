package me.swords1234.chessBot.peices;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.Direction;
import me.swords1234.chessBot.utils.Location;
import me.swords1234.chessBot.utils.Options;
import me.swords1234.chessBot.utils.Type;

public class Knight extends Peice {
    public Knight(Type colorType) {
        super(colorType);
    }

    @Override
    protected Options allowedToMove(Location current, Location newLoc) {
        if (Direction.getDirection(current, newLoc) == Direction.L_SHAPED) {
            return new Options(true, true, false);
        } else {
            return new Options(false);
        }
    }
}
