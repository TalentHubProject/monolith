package org.talenthub.module.xp.listener;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import org.talenthub.module.xp.task.ActivityCalculTask;

@Component
public class IncrementMessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getAuthor().isBot()) return;
        if(!event.getChannelType().equals(ChannelType.TEXT)) return;

        //ActivityCalculTask.getInstance().incrementMessage();
    }
}
