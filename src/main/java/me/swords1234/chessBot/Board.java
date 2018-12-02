package me.swords1234.chessBot;

import me.swords1234.chessBot.peices.*;
import me.swords1234.chessBot.utils.Direction;
import me.swords1234.chessBot.utils.Location;
import me.swords1234.chessBot.utils.Options;
import me.swords1234.chessBot.utils.Type;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Stream;

public class Board {
    private Peice[] peices = new Peice[64];
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

    public boolean requestMove(Peice peice, Location newLocation) {
        if (newLocation.getX() > 7 || newLocation.getX() < 0) return false;
        if (getPeices(newLocation) instanceof None) {
            movePeice(peice, newLocation);
            return true;
        }
        if (peice.getType() != getPeices(newLocation).getType()) {
            //todo Handle kills
            movePeice(peice, newLocation);
            return true;
        }
        return false;
    }

    public boolean move(Peice peice, Location location) {
        Options options = peice.allowedToMove(getLocation(peice), location);
        if (!options.canMove()) return false;
        if (options.checkIfPlayerIsDiagonal()) {
            if (getPeices(location).getType() == peice.getType() || getPeices(location) instanceof None) {
                return false;
            }
        }
        Direction direction = Direction.getDirection(getLocation(peice), location);
        if (!options.canJumpOverOther()) {
            List<Location> locations = direction.getLocations(getLocation(peice), location);
            for (Location loc : locations) {
                if (!(getPeices(loc) instanceof None)) {
                    return false;
                }
            }
        }

        if (options.isCheckInFront()) {
           if (!(getPeices(location) instanceof None)) {
               return false;
           }
        }
        return requestMove(peice, location);
    }


    public void setupBoard() {
        for (int i = 0; i < peices.length; i++) {
            peices[i] = new None();
        }
        for (int i = 0; i < 8; i++) {
            addPeice(new Pawn(Type.BLACK), new Location(i, 6));
            addPeice(new Pawn(Type.WHITE), new Location(i, 1));
        }
        addPeice(new Rook(Type.BLACK), new Location(0, 7));
        addPeice(new Rook(Type.BLACK), new Location(7, 7));
        addPeice(new Rook(Type.WHITE), new Location(0, 0));
        addPeice(new Rook(Type.WHITE), new Location(7, 0));

        addPeice(new Knight(Type.BLACK), new Location(1, 7));
        addPeice(new Knight(Type.BLACK), new Location(6, 7));
        addPeice(new Knight(Type.WHITE), new Location(1, 0));
        addPeice(new Knight(Type.WHITE), new Location(6, 0));

        addPeice(new Bishop(Type.BLACK), new Location(2, 7));
        addPeice(new Bishop(Type.BLACK), new Location(5, 7));
        addPeice(new Bishop(Type.WHITE), new Location(2, 0));
        addPeice(new Bishop(Type.WHITE), new Location(5, 0));

        addPeice(new King(Type.BLACK), new Location(3, 7));
        addPeice(new Queen(Type.BLACK), new Location(4, 7));
        addPeice(new King(Type.WHITE), new Location(4, 0));
        addPeice(new Queen(Type.WHITE), new Location(3, 0));
    }
    String line = "";
    int count = 0;
    int amount = 0;

    public void printBoard() {
        Stream<String> strings = Arrays.stream(peices).map(Peice::printType);
        strings.forEach(str -> {
            if (count >= 8) {
                System.out.println(line);
                line = "";
                count = 0;
            }
            line += str;
            count++;
        });
        System.out.println(line);
        line = "";
    }

    public void addPeice(Peice p, int x, int y) {
        peices[y*height+x] = p;
    }

    public void addPeice(Peice p, Location location) {
        peices[location.getY()*height+location.getX()] = p;
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.setupBoard();
        board.printBoard();
        Peice peice = board.getPeices(0, 1);
        System.out.println(board.move(peice, new Location(0, 3)));
        System.out.println(board.move(peice, new Location(0, 4)));
        System.out.println(board.move(peice, new Location(0, 5)));
        System.out.println(board.move(peice, new Location(1, 6)));
        board.printBoard();
        /*
        Peice p = new Peice(){};
        Peice ps = new Peice(){};
        board.addPeice(p, 5, 3);
        board.addPeice(ps, 7, 6);
        System.out.println(board.requestMove(p, new Location(7, 6)));*/
    }
}
