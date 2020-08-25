package me.swords1234.chessBot.utils;

import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ReactionCollector extends ListenerAdapter {

    private List<Consumer<MessageReactionAddEvent>> consumers = new ArrayList<>();

    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        consumers.forEach(consumer -> consumer.accept(e));
    }

    public void addEvent(Consumer<MessageReactionAddEvent> callBack) {
        consumers.add(callBack);
    }
}
