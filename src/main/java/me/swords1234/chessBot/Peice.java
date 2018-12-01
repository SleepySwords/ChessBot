package me.swords1234.chessBot;

import me.swords1234.chessBot.utils.Direction;
import me.swords1234.chessBot.utils.Type;

public abstract class Peice {
    protected Type type;
    protected Direction[] directionMove;

    public Peice(Type colorType) {
        type = colorType;
    }
}
