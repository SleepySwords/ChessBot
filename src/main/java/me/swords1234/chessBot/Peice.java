package me.swords1234.chessBot;

import me.swords1234.chessBot.utils.Direction;
import me.swords1234.chessBot.utils.Location;

public abstract class Peice {
    protected Type type;
    protected Direction[] directionMove;
}
enum Type {
    BLACK,
    WHITE
}
