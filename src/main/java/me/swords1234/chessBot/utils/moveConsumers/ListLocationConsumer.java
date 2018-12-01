package me.swords1234.chessBot.utils.moveConsumers;

import me.swords1234.chessBot.utils.Location;

import java.util.List;

/*
 * Copyright © 2018 by Ibrahim Hizamul Ansari. All rights reserved.
 * This code may not be copied, reproduced or distributed without permission from the owner.
 * You may contact by his email: Nintendodeveloper8@gmail.com
 * You can also contact him by his Discord: sword1234#6398
 */
@FunctionalInterface
public interface ListLocationConsumer {
    List<Location> accept(Location location, Location loc2);
}
