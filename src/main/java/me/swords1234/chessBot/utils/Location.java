package me.swords1234.chessBot.utils;

public class Location implements Cloneable{
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int xs, int ys) {
        x = xs;
        y = ys;
    }

    public Location clone() throws CloneNotSupportedException {
        return (Location) super.clone();
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}