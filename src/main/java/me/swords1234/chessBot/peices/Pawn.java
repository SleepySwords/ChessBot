package me.swords1234.chessBot.peices;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.*;

public class Pawn extends Peice {
    public Pawn(Type colorType) {
        super(colorType);
    }

    @Override
    protected Options allowedToMove(Location current, Location newLoc) {
        if (MovementDirection.getMovementDirection(current, newLoc, type) == MovementDirection.FORWARD) {
            DirectionDistance directionDistance = Direction.getDistanceDirection(current, newLoc);
            if (directionDistance.getDistance() == 1) {
                if (directionDistance.getDirection() == Direction.STRAIGHT) {
                    return new Options(true);
                } else if (directionDistance.getDirection() == Direction.DIAGONAL){
                    return new Options(true, false, true);
                }
            } else if (directionDistance.getDistance() == 2) {
                if (type == Type.WHITE && current.getY() == 1 && directionDistance.getDirection() == Direction.STRAIGHT) {
                    return new Options(true);
                }
                if (type == Type.BLACK && current.getY() == 6 && directionDistance.getDirection() == Direction.STRAIGHT) {
                    return new Options(true);
                }
            }
        }
        return new Options(false);
    }
}
