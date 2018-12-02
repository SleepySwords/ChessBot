package me.swords1234.chessBot.utils;

import me.swords1234.chessBot.utils.moveConsumers.ListLocationConsumer;
import me.swords1234.chessBot.utils.moveConsumers.MoveBoolean;
import me.swords1234.chessBot.utils.moveConsumers.MoveInt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    }, ((location, loc2) -> {
        int x = location.getX();
        int y = location.getY();
        int x2 = loc2.getX();
        int y2 = loc2.getY();
        List<Location> locations = new ArrayList<>();
        if (Math.max(x, x2) - Math.min(x, x2) == 0) {
            for (int i = Math.min(y, y2) + 1; i < Math.max(y, y2); i++) {
                locations.add(new Location(x2, i));
            }
        } else {
            for (int i = Math.min(x, x2) + 1; i < Math.max(x, x2); i++) {
                locations.add(new Location(i, y2));
            }
        }
        return locations;
    })),


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
    }, ((location, loc2) -> {
        List<Location> locations = new ArrayList<>();
        int x = Math.max(location.getX(), loc2.getX());
        int y = Math.max(location.getY(), loc2.getY());
        int x2 = Math.min(location.getX(), loc2.getX());
        for (int i = 1; i < x-x2; i++) {
            locations.add(new Location(i+x, i+y));
        }
        return locations;
    })),


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
    }, ((location, loc2) -> {
        List<Location> locations = new ArrayList<>();
        locations.add(location);
        locations.add(loc2);
        return locations;
    })),


    NONE(((current, newLoc) -> true), (loc, nLoc) -> {
        //todo Fill this in
        return 0;
    }, ((location, loc2) -> new ArrayList<>()));

    private MoveBoolean movementBoolean;
    private MoveInt distance;
    private ListLocationConsumer listLocationConsumer;
    Direction(MoveBoolean moveBoolean, MoveInt dist, ListLocationConsumer consumer) {
        movementBoolean = moveBoolean;
        distance = dist;
        listLocationConsumer = consumer;
    }

    public List<Location> getLocations(Location current, Location newLoc) {
        return listLocationConsumer.accept(current, newLoc);
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
