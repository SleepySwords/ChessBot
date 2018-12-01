package me.swords1234.chessBot.utils;

@FunctionalInterface
public interface TypeMoveBoolean {
    boolean accept(int x, int y, int x2, int y2, Type type);
}
