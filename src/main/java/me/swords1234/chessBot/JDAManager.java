package me.swords1234.chessBot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class JDAManager extends ListenerAdapter {

    JDA jda;
    private GameManager manager;

    public JDAManager() throws LoginException, InterruptedException {
        jda = new JDABuilder(AccountType.BOT).setToken("NTE5NzIxMzYxMDIzODI3OTY4.XAdKPA.krf8yc-UKNsICKp1wzvv5DU5dzs");
        jda.getPresence().setGame(Game.playing("Chess"));
        jda.addEventListener(this);
        manager = new GameManager(jda);
    }

    public static void main(String[] args) {
        try {
            new JDAManager();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onMessageReceived(MessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split(" ");
        if (args.length <= 0) return;
        if (args[0].length() == 0) return;
        String command = args[0].substring(1);
        if (command.equals("mov")) {
            e.getMessage().delete().queue();
            manager.turn(e.getAuthor(), e.getMessage().getContentRaw());
        }
        if (command.equals("createChess")) {
            System.out.println("HEY!");
            manager.createMatch(e.getAuthor(), e.getMessage().getMentionedUsers().get(0), e.getTextChannel());
        }
    }
}