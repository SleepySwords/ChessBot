package me.swords1234.chessBot.utils.enums;

import me.swords1234.chessBot.utils.Direction;

public class DirectionDistance {
    private Direction direction;
    private int distance;

    public DirectionDistance(Direction direction, int distance) {
        this.direction = direction;
        this.distance = distance;
    }

    public Direction getDirection() {
        return direction;
    }


    @Override
    public String toString() {
        return "DirectionDistance{" +
                "direction=" + direction +
                ", distance=" + distance +
                '}';
    }

    public int getDistance() {
        return distance;
    }
}
