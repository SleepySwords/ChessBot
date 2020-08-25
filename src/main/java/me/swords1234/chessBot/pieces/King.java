package me.swords1234.chessBot.pieces;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.*;
import me.swords1234.chessBot.utils.enums.DirectionDistance;
import me.swords1234.chessBot.utils.enums.MovementDirection;
import me.swords1234.chessBot.utils.enums.Type;

public class King extends Peice {
    public King(Type colorType) {
        super(colorType);
    }

    @Override
    public String printType() {
        if (type == Type.WHITE) {
            return "K";
        }
        return "k";
    }

    @Override
    protected Options allowedToMove(Location current, Location newLoc) {
        DirectionDistance directionDistance = Direction.getDistanceDirection(current, newLoc);
        if (directionDistance.getDistance() == 1) {
            Direction direction = directionDistance.getDirection();
            if (direction == Direction.DIAGONAL || direction == Direction.STRAIGHT) {
                return new Options(true);
            }

        }
        if (directionDistance.getDistance() == 2) {
            Direction direction = directionDistance.getDirection();
            if (direction == Direction.STRAIGHT) {
                MovementDirection movementDirection = MovementDirection.
                        getMovementDirection(current, newLoc, type);
                if (movementDirection == MovementDirection.STATIONARY) {
                    return new Options(true, false,
                            false, false,
                            false, true);
                }
            }
        }
        return new Options(false);
    }
}
