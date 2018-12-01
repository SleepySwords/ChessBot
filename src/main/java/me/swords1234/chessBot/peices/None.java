package me.swords1234.chessBot.peices;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.Location;
import me.swords1234.chessBot.utils.Options;
import me.swords1234.chessBot.utils.Type;

public class None extends Peice {
    public None() {
        super(Type.None);
    }

    @Override
    protected Options allowedToMove(Location current, Location newLoc) {
        return new Options(false);
    }
}
