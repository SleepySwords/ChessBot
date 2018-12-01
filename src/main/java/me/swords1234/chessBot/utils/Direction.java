package me.swords1234.chessBot.utils;

import me.swords1234.chessBot.utils.moveConsumers.MoveBoolean;
import me.swords1234.chessBot.utils.moveConsumers.MoveInt;

public enum Direction {

    STRAIGHT(((current, newLoc) -> {
        int x = current.getX();
        int y = current.getY();
        int x2 = newLoc.getX();
        int y2 = newLoc.getY();
        if (Math.max(x, x2) - Math.min(x, x2) == 0) {
            return true;
        }
        return Math.max(y, y2) - Math.min(y, y2) == 0;
    }), (loc, nLoc) -> {
        int x = loc.getX();
        int y = loc.getY();
        int x2 = nLoc.getX();
        int y2 = nLoc.getY();

        if (Math.max(x, x2) - Math.min(x, x2) == 0) {
            return Math.max(y, y2) - Math.min(y, y2);
        } else {
            return Math.max(x, x2) - Math.min(x, x2);
        }
    }),


    DIAGONAL(((current, newLoc) -> {
        int x = current.getX();
        int y = current.getY();
        int x2 = newLoc.getX();
        int y2 = newLoc.getY();
        int differenceX = Math.max(x, x2) - Math.min(x, x2);
        int differenceY = Math.max(y, y2) - Math.min(y, y2);
        return differenceX == differenceY;
    }), (loc, nLoc) -> {
        //todo Fill this in
        return Math.max(loc.getX(), nLoc.getX()) - Math.min(loc.getX(), nLoc.getX());
    }),


    L_SHAPED(((current, newLoc) -> {
        int x = current.getX();
        int y = current.getY();
        int x2 = newLoc.getX();
        int y2 = newLoc.getY();
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
    }), (loc, nLoc) -> {
        //todo Fill this in
        return 4;
    }),


    NONE(((current, newLoc) -> true), (loc, nLoc) -> {
        //todo Fill this in
        return 0;
    });

    private MoveBoolean movementBoolean;
    private MoveInt distance;
    Direction(MoveBoolean moveBoolean, MoveInt dist) {
        movementBoolean = moveBoolean;
        distance = dist;
    }

    public MoveInt getDistance() {
        return distance;
    }
    public MoveBoolean getMovementCheck() {
        return movementBoolean;
    }

    public static Direction getDirection(Location location, Location newL) {
        for (Direction direction  : Direction.values()) {
            if (direction.getMovementCheck().accept(location, newL)) {
                if (direction == Direction.NONE) continue;
                return direction;
            }
        }
        return Direction.NONE;
    }

    public static DirectionDistance getDistanceDirection(Location location, Location newL) {
        for (Direction direction  : Direction.values()) {
            if (direction.getMovementCheck().accept(location, newL)) {
                if (direction == Direction.NONE) continue;
                int distance = direction.getDistance().accept(location, newL);
                return new DirectionDistance(direction, distance);
            }
        }
        return new DirectionDistance(Direction.NONE, 0);
    }
}
