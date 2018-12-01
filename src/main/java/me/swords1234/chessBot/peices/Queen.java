package me.swords1234.chessBot.peices;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.Direction;
import me.swords1234.chessBot.utils.Type;

public class Queen extends Peice {
    public Queen(Type type) {
        super(type);
        directionMove = new Direction[2];
        directionMove[0] = Direction.DIAGONAL;
        directionMove[1] = Direction.STRAIGHT;
    }
}
