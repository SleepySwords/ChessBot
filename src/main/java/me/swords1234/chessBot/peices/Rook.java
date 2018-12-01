package me.swords1234.chessBot.peices;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.Direction;
import me.swords1234.chessBot.utils.Location;
import me.swords1234.chessBot.utils.Type;

public class Rook extends Peice {
    public Rook(Type colorType) {
        super(colorType);
    }

    @Override
    public boolean canJumpOverEnimies() {
        return false;
    }

    @Override
    protected boolean allowedToMove(Location current, Location newLoc) {
        return Direction.getDirection(current, newLoc) == Direction.STRAIGHT;
    }
}
