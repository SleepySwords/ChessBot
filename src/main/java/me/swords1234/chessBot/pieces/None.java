package me.swords1234.chessBot.pieces;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.Location;
import me.swords1234.chessBot.utils.Options;
import me.swords1234.chessBot.utils.enums.Type;

public class None extends Peice {
    public None() {
        super(Type.None);
    }

    @Override
    public String printType() {
        return "*";
    }

    @Override
    protected Options allowedToMove(Location current, Location newLoc) {
        return new Options(false);
    }
}
