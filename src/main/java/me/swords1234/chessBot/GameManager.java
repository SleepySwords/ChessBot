package me.swords1234.chessBot;

import me.swords1234.chessBot.utils.BoardUser;
import me.swords1234.chessBot.utils.ErrorOptions;
import me.swords1234.chessBot.utils.Location;
import me.swords1234.chessBot.utils.ReactionCollector;
import me.swords1234.chessBot.utils.enums.Type;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameManager {

    private Map<User, BoardUser> boards;
    private ReactionCollector collector;
    private ImageGen imageGen;
    Pattern pattern = Pattern.compile("\\.mov (\\w\\d) (\\w\\d)", Pattern.CASE_INSENSITIVE);

    public GameManager(JDA jda) {
        boards = new HashMap<>();
        imageGen = new ImageGen();
        collector = new ReactionCollector();
        jda.addEventListener(collector);
    }

    /*public static void main(String[] args) {

        ImageGen gen = new ImageGen();


        turn = Type.WHITE;
        Board board = new Board();
        board.setupBoard();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            board.printBoard();

                Peice peice = board.getPeices(current);
                if (peice.type != turn) {
                    System.out.println("Not your piece!");
                    continue;
                }


            String str = scanner.nextLine();
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                char letter = matcher.group(1).charAt(0);
                int num = Integer.parseInt(String.valueOf(matcher.group(1).charAt(1)));
                int yPos = num - 1;
                int xPos = (int) letter - 97;
                Location current = new Location(xPos, yPos);

                char letter2 = matcher.group(2).charAt(0);
                int num2 = Integer.parseInt(String.valueOf(matcher.group(2).charAt(1)));
                int y2Pos = num2 - 1;
                int x2Pos = (int) letter2 - 97;
                Location newPos = new Location(x2Pos, y2Pos);

                System.out.println("x: " + current.getX() + " y: " + current.getY());
                System.out.println("x: " + newPos.getX() + " y: " + newPos.getY());

                if (!board.move(board.getPeices(current), newPos)) {
                    System.out.println("INVALID MOVE!");
                    continue;
                }

                File file = new File("./hi" + ".png");
                try {
                    ImageIO.write(gen.genBoth(board.getPeices(), board.getRemovedPeice()), "png", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (turn == Type.WHITE) {
                    turn = Type.BLACK;
                } else {
                    turn = Type.WHITE;
                }
            }
        }
    }*/

    public void createMatch(User host, User opponent, TextChannel channel) {
        Board board = new Board(channel, collector, imageGen, boards.size());
        BoardUser combine = new BoardUser(board, host, opponent, channel);
        boards.put(host, combine);
        boards.put(opponent, combine);
        board.test();

        board.genImage();
    }

    public boolean turn(User user, String message) {
        if (!boards.containsKey(user)) {
            return false;
        }
        Matcher matcher = pattern.matcher(message);

        if (matcher.matches()) {
            char letter = matcher.group(1).charAt(0);
            int num = Integer.parseInt(String.valueOf(matcher.group(1).charAt(1)));
            int yPos = num - 1;
            int xPos = (int) letter - 97;
            Location current = new Location(xPos, yPos);

            char letter2 = matcher.group(2).charAt(0);
            int num2 = Integer.parseInt(String.valueOf(matcher.group(2).charAt(1)));
            int y2Pos = num2 - 1;
            int x2Pos = (int) letter2 - 97;
            Location newPos = new Location(x2Pos, y2Pos);

            BoardUser users =  boards.get(user);
            Board board = users.getBoard();
            Type type = users.getHost().equals(user) ? Type.WHITE : Type.BLACK;
            ErrorOptions response = board.move(board.getPeices(current), newPos, type, user);
            if (response == ErrorOptions.INVALID_MOVE) {
                users.getBaseChannel().sendMessage("**INVALID MOVE!**").queue(msg -> msg.delete().queueAfter(10, TimeUnit.SECONDS));
                return true;
            }

            if (response == ErrorOptions.NOT_YOUR_PIECE) {
                users.getBaseChannel().sendMessage("**NOT YOUR PIECE!**").queue(msg -> msg.delete().queueAfter(10, TimeUnit.SECONDS));
                return true;
            }
            board.genImage();
            //Since the current play change, (Because the turn just passes) we have to check the opposite of out player.
            if (board.isCheck(true, false)) {
                //board.printWinEmbed(board.getCurrentPlayer());
                boards.remove(
                        boards.get(user).getHost());
                boards.remove(user);
            }
            //Since the current play change, (Because the turn just passes) we have to our player.
            if (board.isCheck(false, true)) {
                //board.printWinEmbed(board.getCurrentPlayer() == Type.WHITE ? Type.BLACK : Type.WHITE);
                boards.remove(
                        boards.get(user).getHost());
                boards.remove(user);
            }
            return true;
        }
        return false;
    }
}
