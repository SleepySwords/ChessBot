package me.swords1234.chessBot.peices;

import me.swords1234.chessBot.Peice;
import me.swords1234.chessBot.utils.*;

import javax.swing.text.html.Option;

public class King extends Peice {
    public King(Type colorType) {
        super(colorType);
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
        return new Options(false);
    }
}
