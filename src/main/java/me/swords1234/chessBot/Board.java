package me.swords1234.chessBot;

import me.swords1234.chessBot.peices.None;
import me.swords1234.chessBot.utils.Location;

import java.util.Optional;
import java.util.Vector;

public class Board {
    private Peice[] peices = new Peice[63];
    private final int height = 8;

    public Peice getPeices(int x, int y) {
        return peices[y*height+x];
    }

    public Peice getPeices(Location location) {
        return getPeices(location.getX(), location.getY());
    }

    public Location getLocation(Peice peice) {
        int point = 0;
        for (Peice found : peices) {
            if (found.equals(peice)) {
                return new Location(point%8, point/8);
            }
            point++;
        }
        return null;
    }

    private void movePeice(Peice peice, Location newLoc) {
        Location loc = getLocation(peice);
        peices[newLoc.getY()*height+newLoc.getX()] = peice;
        peices[loc.getY()*height+loc.getX()] = new None();
    }

    public Board() {
        System.out.println(peices[1]);
    }

    public void setupBoard() {
        for (int i = 0; i < peices.length; i++) {
            peices[i] = new None();
        }
    }

    public boolean requestMove(Peice peice, Location newLocation) {
        getLocation(peice);
        if (getPeices(newLocation) instanceof None) {
            movePeice(peice, newLocation);
            return true;
        }
        return false;
    }

    public void addPeice(Peice p, int x, int y) {
        peices[y*height+x] = p;
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.setupBoard();
        /*
        Peice p = new Peice(){};
        Peice ps = new Peice(){};
        board.addPeice(p, 5, 3);
        board.addPeice(ps, 7, 6);
        System.out.println(board.requestMove(p, new Location(7, 6)));*/
    }
}
