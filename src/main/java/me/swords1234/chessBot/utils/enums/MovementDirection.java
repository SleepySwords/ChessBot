package me.swords1234.chessBot.utils.enums;
/*
 * Copyright © 2018 by Ibrahim Hizamul Ansari. All rights reserved.
 * This code may not be copied, reproduced or distributed without permission from the owner.
 * You may contact by his email: Nintendodeveloper8@gmail.com
 * You can also contact him by his Discord: sword1234#6398
 */

import me.swords1234.chessBot.utils.Location;
import me.swords1234.chessBot.utils.moveConsumers.TypeMoveBoolean;

public enum MovementDirection {
    STATIONARY((loc, newLoc, type) -> loc.getY() == newLoc.getY()),
    FORWARD((loc, newLoc, type) -> {
        int y = loc.getY();
        int y2 = newLoc.getY();
        if (type == Type.WHITE) {
            return y < y2;
        } else {
            return y > y2;
        }
    }),

    BACKWARD((loc, newLoc, type) -> {
        int y = loc.getY();
        int y2 = newLoc.getY();
        if (type == Type.WHITE) {
            return y > y2;
        } else {
            return y < y2;
        }
    }),

    NONE((loc, newLoc, type) -> true);

    TypeMoveBoolean mBoolean;
    MovementDirection(TypeMoveBoolean moveBoolean) {
        mBoolean = moveBoolean;
    }

    public static MovementDirection getMovementDirection(Location current, Location newLoc, Type type) {
        for (MovementDirection movementDirection : MovementDirection.values()) {
            if (movementDirection.mBoolean.accept(current, newLoc, type)) {
                if (movementDirection == MovementDirection.NONE) continue;
                return movementDirection;
            }
        }
        return MovementDirection.NONE;
    }
}
