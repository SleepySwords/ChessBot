package me.swords1234.chessBot.utils;
/*
 * Copyright © 2018 by Ibrahim Hizamul Ansari. All rights reserved.
 * This code may not be copied, reproduced or distributed without permission from the owner.
 * You may contact by his email: Nintendodeveloper8@gmail.com
 * You can also contact him by his Discord: sword1234#6398
 */

public enum MovementDirection {
    STATIONARY((x, y, x2, y2, type) -> y == y2),
    FORWARD((x, y, x2, y2, type) -> {
        if (type == Type.WHITE) {
            return x < x2;
        } else {
            return x > x2;
        }
    }),
    BACKWARD((x, y, x2, y2, type) -> {
        if (type == Type.WHITE) {
            return x > x2;
        } else {
            return x < x2;
        }
    });


    TypeMoveBoolean mBoolean;
    MovementDirection(TypeMoveBoolean moveBoolean) {
        mBoolean = moveBoolean;
    }
}
