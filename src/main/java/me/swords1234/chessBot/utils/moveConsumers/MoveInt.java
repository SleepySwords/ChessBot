package me.swords1234.chessBot.utils.moveConsumers;

import me.swords1234.chessBot.utils.Location;

@FunctionalInterface
public interface MoveInt {
    int accept(Location location, Location newLocation);
}
