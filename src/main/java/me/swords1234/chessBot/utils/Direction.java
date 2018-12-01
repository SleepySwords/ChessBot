package me.swords1234.chessBot.utils;

import me.swords1234.chessBot.Peice;

public enum Direction {

    STRAIGHT(((x, y, x2, y2) -> {
        if (Math.max(x, x2) - Math.min(x, x2) == 1) {
            return true;
        }
        return Math.max(y, y2) - Math.min(y, y2) == 1;
    })),
    DIAGONAL(((x, y, x2, y2) -> {
        int differenceX = Math.max(x, x2) - Math.min(x, x2);
        int differenceY = Math.max(y, y2) - Math.min(y, y2);
        return differenceX == differenceY;
    })),
    L_SHAPED(((x, y, x2, y2) -> {
        int differenceX = Math.max(x, x2) - Math.min(x, x2);
        int differenceY = Math.max(y, y2) - Math.min(y, y2);

        if (differenceX == 1) {
            if (differenceY == 3) {
                return true;
            }
        }

        if (differenceY == 1) {
            if (differenceX == 3) {
                return true;
            }
        }
        return true;
    })),
    NONE(((x, y, x2, y2) -> true));

    private MoveBoolean movementBoolean;
    Direction(MoveBoolean moveBoolean) {
        movementBoolean = moveBoolean;
    }

    public MoveBoolean getMovementCheck() {
        return movementBoolean;
    }

    static Direction getDirection(int x, int y, int x2, int y2) {
        for (Direction direction  : Direction.values()) {
            if (direction.getMovementCheck().accept(x, y, x2, y2)) {
                if (direction == Direction.NONE) continue;
                return direction;
            }
        }
        return Direction.NONE;
    }
}
