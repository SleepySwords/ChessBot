package me.swords1234.chessBot.utils;

public class Options {

    private boolean canMove;
    private boolean canJumpOverOther;
    private boolean checkIfPlayerIsDiagonal;

    public Options(boolean canMove, boolean canJumpOverOther, boolean checkIfPlayerIsDiagonal) {
        this.canMove = canMove;
        this.canJumpOverOther = canJumpOverOther;
        this.checkIfPlayerIsDiagonal = checkIfPlayerIsDiagonal;
    }

    public Options(boolean canMove) {
        this.canMove = canMove;
        canJumpOverOther = false;
        checkIfPlayerIsDiagonal = false;
    }

    public boolean canMove() {
        return canMove;
    }

    public boolean canJumpOverOther() {
        return canJumpOverOther;
    }

    public boolean checkIfPlayerIsDiagonal() {
        return checkIfPlayerIsDiagonal;
    }
}
