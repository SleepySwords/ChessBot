package me.swords1234.chessBot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class JDAManager extends ListenerAdapter {

    JDA jda;

    public JDAManager() throws LoginException, InterruptedException {
        jda = new JDABuilder(AccountType.BOT).setToken("TOKEN_HERE").buildBlocking();
        jda.getPresence().setGame(Game.playing("Chess"));
        jda.addEventListener(this);
    }

    public void onMessageRecieved(MessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split(" ");
        String command = args[0].substring(1);
        if (command.equals("mov")) {
            
        }
    }
}
