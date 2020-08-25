package me.swords1234.chessBot;

import me.swords1234.chessBot.pieces.*;
import me.swords1234.chessBot.utils.*;
import me.swords1234.chessBot.utils.enums.DirectionDistance;
import me.swords1234.chessBot.utils.enums.Type;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Board {
    private final int gameIDs;
    private ReactionCollector collector;
    private Peice[] peices = new Peice[64];
    private List<Peice> removedPeice = new ArrayList<>();
    private final int height = 8;
    private Type currentPlayer = Type.WHITE;

    private TextChannel baseChannel;
    private Message message;
    private ImageGen gen;

    public Board(TextChannel baseChannel, ReactionCollector collector, ImageGen gen, int gameId) {
        gameIDs = gameId;
        this.baseChannel = baseChannel;
        this.gen = gen;
        this.collector = collector;
    }

    public List<Peice> getRemovedPeice() {
        return removedPeice;
    }

    private Peice getPeices(int x, int y) {
        return peices[y*height+x];
    }

    public Peice getPeices(Location location) {
        return getPeices(location.getX(), location.getY());
    }

    public Type getCurrentPlayer() {
        return currentPlayer;
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
        peice.move();
    }

    private boolean requestMove(Peice peice, Location newLocation) {
        if (newLocation.getX() > 7 || newLocation.getX() < 0) {
            System.out.println("Hey!");
            return false;
        }
        System.out.println(getPeices(newLocation) instanceof None);
        if (getPeices(newLocation) instanceof None) {
            System.out.println("Hey!");
            movePeice(peice, newLocation);
            return true;
        }
        if (peice.getType() != getPeices(newLocation).getType()) {
            //todo Handle kills
            removedPeice.add(getPeices(newLocation));
            movePeice(peice, newLocation);
            return true;
        }
        return false;
    }

    public ErrorOptions move(Peice peice, Location location, Type type, User user) {
        //todo make it so it checks the actual player
        if (currentPlayer != type) {
            return ErrorOptions.NOT_YOUR_PIECE;
        }
        Location peiceLoc = getLocation(peice);

        Options options = peice.allowedToMove(getLocation(peice), location);
        if (!options.canMove()) return ErrorOptions.INVALID_MOVE;
        if (options.checkIfPlayerIsDiagonal()) {
            if (getPeices(location).getType() == peice.getType() || getPeices(location) instanceof None) {
                return ErrorOptions.INVALID_MOVE;
            }
        }
        Location pLoc = getLocation(peice);
        Direction direction = Direction.getDirection(pLoc, location);
        if (!options.canJumpOverOther()) {
            List<Location> locations = direction.getLocations(pLoc, location);
            for (Location loc : locations) {
                if (!(getPeices(loc) instanceof None)) {
                    return ErrorOptions.INVALID_MOVE;
                }
            }
        }
        if (options.isCheckInFront()) {
           if (!(getPeices(location) instanceof None)) {
               return ErrorOptions.INVALID_MOVE;
           }
        }
        if (options.isCastling()) {
            Rook rook = null;
            int distance = 0;
            for (Peice castle : peices) {
                if (castle instanceof Rook) {
                    if (castle.getType() == currentPlayer) {
                        DirectionDistance directionDistance = Direction.getDistanceDirection(
                                getLocation(castle), location);
                        System.out.println("Hey!");
                        System.out.println(directionDistance.getDistance());
                        if (directionDistance.getDistance() < distance || distance == 0) {
                            System.out.println("Hey2!");
                            distance = directionDistance.getDistance();
                            rook = (Rook) castle;
                        }
                    }
                }
            }
            if (rook == null) {
                return ErrorOptions.INVALID_MOVE;
            }
            if (rook.hasMoved()) {
                return ErrorOptions.INVALID_MOVE;
            }
            if (peice.hasMoved()) {
                return ErrorOptions.INVALID_MOVE;
            }
            DirectionDistance directionDistance = Direction.getDistanceDirection(getLocation(rook), location);
            List<Location> locations = directionDistance.getDirection().getLocations(peiceLoc, location);
            locations.add(peiceLoc);
            locations.add(location);
            for (Location loc : locations) {
                if (isCheckAtLoc(loc, true)) {
                    return ErrorOptions.INVALID_MOVE;
                }
            }
            Location loc = null;
            if (peiceLoc.getX() < location.getX()) {
                try {
                    loc = location.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                assert loc != null;
                loc.move(location.getX()-1, location.getY());
            } else {
                try {
                    loc = location.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                assert loc != null;
                loc.move(location.getX()+1, location.getY());
            }
            requestMove(rook, loc);
        }

        if (requestMove(getPeices(pLoc), location)){
            if (options.isPromotion()) {
                UpgradePeice(location, type, user);
            }
            if (currentPlayer == Type.WHITE) {
                currentPlayer = Type.BLACK;
            } else if (currentPlayer == Type.BLACK) {
                currentPlayer = Type.WHITE;
            }
            return ErrorOptions.NONE;
        }
        return ErrorOptions.INVALID_MOVE;
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

    void printBoard() {
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

    public void test() {
        for (int i = 0; i < peices.length; i++) {
            peices[i] = new None();
        }
        /*addPeice(new King(Type.BLACK), new Location(0, 0));
        addPeice(new Rook(Type.WHITE), new Location(0, 2));
        addPeice(new Rook(Type.WHITE), new Location(0, 1));
        addPeice(new Rook(Type.WHITE), new Location(2, 0));
        addPeice(new Rook(Type.WHITE), new Location(1, 0));
        addPeice(new Bishop(Type.WHITE), new Location(1, 1));*//*
        addPeice(new King(Type.WHITE), new Location(0, 0));
        addPeice(new King(Type. BLACK), new Location(7, 7));
        addPeice(new Pawn(Type. BLACK), new Location(7, 1));
        addPeice(new Pawn(Type. WHITE), new Location(0, 6));*/
        addPeice(new King(Type.BLACK), new Location(7, 7));
        addPeice(new Rook(Type.BLACK), new Location(6, 7));
        addPeice(new Rook(Type.WHITE), new Location(0, 0));
        addPeice(new King(Type.WHITE), new Location(4, 0));
        addPeice(new Rook(Type.WHITE), new Location(7, 0));
    }

    Peice[] getPeices() {
        return peices;
    }

    public void UpgradePeice(Location newLoc, Type type, User user) {
        Message msg = baseChannel.sendMessage("Upgrade pawn at Location: X: " + newLoc.getX() + " Y: " + newLoc.getY() + ". Options: Knight, Queen, Rook and Bishop" +
                "\nKnight = :one:, Queen = :two:, Rook = :three:, Bishop = :four:").complete();
        msg.addReaction("1⃣").complete();
        msg.addReaction("2⃣").complete();
        msg.addReaction("3⃣").complete();
        msg.addReaction("4⃣").complete();
        boolean isDone = true;
        while (isDone) {
            Message updated = baseChannel.getMessageById(msg.getId()).complete();
            //System.out.println(updated.getReactions());
            for (MessageReaction e : updated.getReactions()){
                if (e.getUsers().complete().contains(user)) {
                    Peice peice = null;
                    if (e.getReactionEmote().getName().equals("1⃣")) {
                        peice = new Knight(type);
                    }
                    if (e.getReactionEmote().getName().equals("2⃣")) {
                        peice = new Queen(type);
                    }
                    if (e.getReactionEmote().getName().equals("3⃣")) {
                        peice = new Rook(type);
                    }
                    if (e.getReactionEmote().getName().equals("4⃣")) {
                        peice = new Bishop(type);
                    }
                    Character.isAlphabetic('j');
                    addPeice(peice, newLoc);
                    updated.delete().complete();
                    isDone = false;
                    break;
                }
            }
            System.out.println(isDone);
        }
        System.out.println("Broken out!");
    }

    public void genImage() {
        File file = new File("./temp_" + gameIDs + ".png");
        try {
            ImageIO.write(gen.genBoth(peices, removedPeice), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (message != null) {
            message.editMessage("Loading...").queue();
        }
        String url =  Util.fileToUrl(file);
        MessageEmbed embed = new EmbedBuilder().setImage(url).setColor(Color.RED).build();
        if (message == null) {
            message = baseChannel.sendMessage(embed).complete();
        } else {
            message.editMessage("Updated!").queue();
            message.editMessage(embed).queue();
        }
    }

    public void addPeice(Peice p, int x, int y) {
        peices[y*height+x] = p;
    }

    public void addPeice(Peice p, Location location) {
        peices[location.getY()*height+location.getX()] = p;
    }

    public boolean isCheck(boolean check, boolean currentPlayers) {
        Type type = currentPlayers ? currentPlayer : Type.WHITE == currentPlayer ? Type.BLACK : Type.WHITE;
        for (Peice peice : peices) {
            if (peice.getType() == type && peice instanceof King) {
                Location kingLoc = getLocation(peice);
                List<Location> checkLocation = check ? Collections.singletonList(kingLoc) : getAround(kingLoc);
                List<Location> allTakenLocations = new ArrayList<>();
                for (Peice checkPeice : peices) {
                    if (checkPeice.getType() == type) continue;
                    Location checkPeiceLocation = getLocation(checkPeice);

                    for (Location location : checkLocation) {
                        Options op = checkPeice.allowedToMove(checkPeiceLocation, location);
                        Direction direction = Direction.getDirection(checkPeiceLocation, location);

                        if (op.canMove()) {
                            if (!op.canJumpOverOther()) {
                                List<Location> locations = direction.getLocations(checkPeiceLocation, location);
                                boolean test = true;
                                for (Location loc : locations) {
                                    if (!(getPeices(loc) instanceof None)) {
                                        test = false;
                                    }
                                }
                                if (!test) {
                                    continue;
                                }
                            }
                            if (op.isCheckInFront()) {
                                if (!(checkPeice instanceof Pawn)) {
                                    continue;
                                }
                            }
                            if (!allTakenLocations.contains(location)) {
                                allTakenLocations.add(location);
                            }
                        }
                    }
                }
                //System.out.println(checkLocation.size());
                //System.out.println("ALL: " + allTakenLocations.size());
                if (checkLocation.size() <= allTakenLocations.size()) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    public boolean isCheckAtLoc(Location location, boolean currentPlayers) {
        List<Location> allTakenLocations = new ArrayList<>();
        Type type = currentPlayers ? currentPlayer : currentPlayer == Type.WHITE ? Type.BLACK
                : Type.WHITE;
        for (Peice checkPeice : peices) {
            if (checkPeice.getType() == type) continue;
            Location checkPeiceLocation = getLocation(checkPeice);

            Options op = checkPeice.allowedToMove(checkPeiceLocation, location);
            Direction direction = Direction.getDirection(checkPeiceLocation, location);

            if (op.canMove()) {
                if (!op.canJumpOverOther()) {
                    List<Location> locations = direction.getLocations(checkPeiceLocation, location);
                    boolean test = true;
                    for (Location loc : locations) {
                        if (!(getPeices(loc) instanceof None)) {
                            test = false;
                        }
                    }
                    if (!test) {
                        continue;
                    }
                }
                if (op.isCheckInFront()) {
                    if (!(checkPeice instanceof Pawn)) {
                        continue;
                    }
                }
                if (!allTakenLocations.contains(location)) {
                    allTakenLocations.add(location);
                }
            }
        }
        System.out.println("ALL Found: " + allTakenLocations.size());
        return 1 <= allTakenLocations.size();
    }

    public List<Location> getAround(Location location) {
        List<Location> locations = new ArrayList<>();
        if (isOnBoard(new Location(location.getX()-1, location.getY()))) {
            locations.add(new Location(location.getX() - 1, location.getY()));
        }
        if (isOnBoard(new Location(location.getX() - 1, location.getY() - 1))) {
            locations.add(new Location(location.getX()-1, location.getY() - 1));
        }
        if (isOnBoard(new Location(location.getX(), location.getY() - 1))) {
            locations.add(new Location(location.getX(), location.getY() - 1));
        }
        if (isOnBoard(new Location(location.getX()+1, location.getY() - 1))) {
            locations.add(new Location(location.getX() + 1, location.getY() - 1));
        }
        if (isOnBoard(new Location(location.getX()+1, location.getY()))) {
            locations.add(new Location(location.getX() + 1, location.getY()));
        }
        if (isOnBoard(new Location(location.getX()+1, location.getY() + 1))) {
            locations.add(new Location(location.getX() + 1, location.getY() + 1));
        }
        if (isOnBoard(new Location(location.getX(), location.getY() + 1))) {
            locations.add(new Location(location.getX(), location.getY() + 1));
        }
        if (isOnBoard(new Location(location.getX() - 1, location.getY() + 1))) {
            locations.add(new Location(location.getX() - 1, location.getY() + 1));
        }
        if (getAmount(getPeices(location).getType()) > 1) {
            locations.add(location);
        }
        return locations;
    }

    public int getAmount(Type type) {
        int count = 0;
        for (Peice peice : peices) {
            if (peice.getType() == type) {
                count++;
            }
        }
        return count;
    }

    public boolean isOnBoard(Location location){
        if (location.getY() > height-1) {
            return false;
        }
        if (location.getY() < 0) {
            return false;
        }
        if (location.getX() > height-1) {
            return false;
        }
        return location.getX() >= 0;
    }
}
