package org.talenthub.module.xp.listener;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import org.talenthub.module.xp.service.LevelService;

@Component
@AllArgsConstructor
public class TextMessageListener extends ListenerAdapter {

    private final LevelService levelService;

    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {

        if(event.getAuthor().isBot()) return;
        if(!event.getChannelType().equals(ChannelType.TEXT)) return;

        int xpEarned = event.getMessage().getContentRaw().split(" ").length;

        levelService.addXp(event.getChannel().asTextChannel(), event.getMember(), xpEarned);

    }
}
