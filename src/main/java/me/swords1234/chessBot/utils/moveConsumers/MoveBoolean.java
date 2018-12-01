package me.swords1234.chessBot.utils.moveConsumers;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.Location;

@FunctionalInterface
public interface MoveBoolean{
    boolean accept(Location current, Location newLoc);
}
