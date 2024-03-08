package org.talenthub.module.xp.listener;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import org.talenthub.exception.ConfigException;
import org.talenthub.module.xp.model.WishWelcome;
import org.talenthub.module.xp.service.LevelService;
import org.talenthub.module.xp.task.WishWelcomeTask;
import org.talenthub.service.ConfigService;

@Component
@AllArgsConstructor
public class WishWelcomeListener extends ListenerAdapter {

    private final LevelService levelService;
    private final ConfigService configService;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getAuthor().isBot()) return;
        if(!event.getChannelType().equals(ChannelType.TEXT)) return;
        if(event.getMessage().getReferencedMessage() == null) return;

        // Get the WishWelcome
        WishWelcome wishWelcome = WishWelcomeTask.getWishWelcome(event.getMessage().getReferencedMessage().getIdLong());

        // If the WishWelcome is not found, return
        if(wishWelcome == null) return;

        // Check if the message not contains mention of the new user
        for(User user : event.getMessage().getReferencedMessage().getMentions().getUsers()){
            if(user.getIdLong() == event.getAuthor().getIdLong()){
                return;
            }
        }

        // If the user has already responded, return
        if(wishWelcome.getResponseUserIds().contains(event.getAuthor().getIdLong())) return;

        // Get the amount of xp earned
        int xpEarned;
        try {
            xpEarned = configService.getInt("wish-welcome-gain-xp");
        }catch (ConfigException e){
            xpEarned = 12;
        }

        // Add the xp to the user
        //fix(Leonarddoo): Le système de gain d'xp est à revoir
        //levelService.addXp(event.getChannel().asTextChannel(), event.getMember(), xpEarned);

        // Add reaction to show the user gain xp
        event.getMessage().addReaction(Emoji.fromUnicode("U+2728")).queue();

        // Add the user to the list of users who have responded
        wishWelcome.getResponseUserIds().add(event.getAuthor().getIdLong());
    }
}
