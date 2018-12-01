package me.swords1234.chessBot.utils;

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

    public int getDistance() {
        return distance;
    }
}
