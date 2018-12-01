package me.swords1234.chessBot.peices;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.Location;
import me.swords1234.chessBot.utils.Type;

public class King extends Peice {
    public King(Type colorType) {
        super(colorType);
    }

    @Override
    public boolean canJumpOverEnimies() {
        return false;
    }

    @Override
    protected boolean allowedToMove(Location current, Location newLoc) {
        return false;
    }
}
