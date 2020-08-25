package me.swords1234.chessBot.utils.moveConsumers;

import me.swords1234.chessBot.utils.Location;
import me.swords1234.chessBot.utils.enums.Type;

@FunctionalInterface
public interface TypeMoveBoolean {
    boolean accept(Location location, Location newLoc, Type type);
}
