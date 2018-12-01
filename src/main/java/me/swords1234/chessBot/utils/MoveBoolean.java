package me.swords1234.chessBot.utils;

import me.swords1234.chessBot.Peice;

@FunctionalInterface
public interface MoveBoolean{
    boolean accept(int x, int y, int x2, int y2);
}
