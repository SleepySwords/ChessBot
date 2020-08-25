package me.swords1234.chessBot.utils;

import me.swords1234.chessBot.Board;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class BoardUser {
    private Board board;
    private final User host;
    private final User opponent;
    private final TextChannel baseChannel;

    public Board getBoard() {
        return board;
    }

    public User getHost() {
        return host;
    }

    public User getOpponent() {
        return opponent;
    }

    public TextChannel getBaseChannel() {
        return baseChannel;
    }

    public BoardUser(Board board, User host, User opponent, TextChannel baseChannel) {
        this.board = board;
        this.host = host;
        this.opponent = opponent;
        this.baseChannel = baseChannel;
    }
}
