package me.swords1234.chessBot.utils;

public class Options {

    private boolean canMove;
    private boolean canJumpOverOther;
    private boolean checkIfPlayerIsDiagonal;
    private boolean checkInFront;

    public Options(boolean canMove, boolean canJumpOverOther, boolean checkIfPlayerIsDiagonal) {
        this.canMove = canMove;
        this.canJumpOverOther = canJumpOverOther;
        this.checkIfPlayerIsDiagonal = checkIfPlayerIsDiagonal;
    }

    public Options(boolean canMove, boolean canJumpOverOther, boolean checkIfPlayerIsDiagonal, boolean checkInFront) {
        this.canMove = canMove;
        this.canJumpOverOther = canJumpOverOther;
        this.checkIfPlayerIsDiagonal = checkIfPlayerIsDiagonal;
        this.checkInFront = checkInFront;
    }

    public Options(boolean canMove) {
        this.canMove = canMove;
        canJumpOverOther = false;
        checkIfPlayerIsDiagonal = false;
    }

    public boolean canMove() {
        return canMove;
    }

    public boolean isCheckInFront() {
        return checkInFront;
    }

    public boolean canJumpOverOther() {
        return canJumpOverOther;
    }

    public boolean checkIfPlayerIsDiagonal() {
        return checkIfPlayerIsDiagonal;
    }
}
