package me.swords1234.chessBot.pieces;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.*;
import me.swords1234.chessBot.utils.enums.DirectionDistance;
import me.swords1234.chessBot.utils.enums.MovementDirection;
import me.swords1234.chessBot.utils.enums.Type;

public class Pawn extends Peice {
    public Pawn(Type colorType) {
        super(colorType);
    }

    @Override
    public String printType() {
        if (type == Type.WHITE) {
            return "P";
        }
        return "p";
    }

    @Override
    protected Options allowedToMove(Location current, Location newLoc) {
        if (MovementDirection.getMovementDirection(current, newLoc, type) == MovementDirection.FORWARD) {
            DirectionDistance directionDistance = Direction.getDistanceDirection(current, newLoc);
            boolean promotion = false;
            if ((newLoc.getY() == 7 && type == Type.WHITE) || (newLoc.getY() == 0 && type == Type.BLACK)) {
                promotion = true;
            }
            if (directionDistance.getDistance() == 1) {
                if (directionDistance.getDirection() == Direction.STRAIGHT) {
                    return new Options(true, false, false, true, promotion);
                } else if (directionDistance.getDirection() == Direction.DIAGONAL){
                    return new Options(true, false, true, false, promotion);
                }
            } else if (directionDistance.getDistance() == 2) {
                if (type == Type.WHITE && current.getY() == 1 && directionDistance.getDirection() == Direction.STRAIGHT) {
                    return new Options(true, false, false, true, promotion);
                }
                if (type == Type.BLACK && current.getY() == 6 && directionDistance.getDirection() == Direction.STRAIGHT) {
                    return new Options(true, false, false, true, promotion);
                }
            }
        }
        return new Options(false);
    }
}
