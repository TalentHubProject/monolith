package org.talenthub.module.welcome.listener;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import org.talenthub.module.xp.model.WishWelcome;
import org.talenthub.module.xp.task.WishWelcomeTask;
import org.talenthub.service.ConfigService;

import java.util.Objects;

@Component
@AllArgsConstructor
public class PlayerJoinGuildListener extends ListenerAdapter {

    private final ConfigService configService;

    @Override
    public void onGuildMemberJoin(final GuildMemberJoinEvent event) {

        String welcomeChannelId = configService.getString("welcome-channel-id");

        TextChannel welcomeChannel;
        if(!welcomeChannelId.isEmpty()){
            // Get the welcome channel in the config
            welcomeChannel = event.getGuild().getTextChannelById(configService.getString("welcome-channel-id"));

            // If the channel is not found, use the default channel of the guild
            Objects.requireNonNullElse(welcomeChannel, event.getGuild().getDefaultChannel());
        }else{
            // Use the default channel of the guild
            welcomeChannel = event.getGuild().getDefaultChannel().asTextChannel();
        }

        // Send the welcome message
        welcomeChannel.sendMessage("""
                *%s, vient de rejoindre le serveur ! Répondez à ce message pour lui souhaiter la bienvenue et gagner de l'expérience.* ✨
                Bienvenue sur %s, %s ! ? Empowering Creators, Uniting Passions, Inspriring Growth ? ; construisons ensemble des projets ambitieux, main dans la main.
                """
                .formatted(
                        event.getMember().getEffectiveName(),
                        event.getGuild().getName(),
                        event.getMember().getAsMention()
                )
        ).queue(welcomeMessage -> {
            // Create WishWelcome
            WishWelcome wishWelcome = new WishWelcome(welcomeMessage.getIdLong(), event.getMember().getIdLong());
            // Execute the task
            new WishWelcomeTask(welcomeMessage.getIdLong()).execute(wishWelcome);
        });



    }
}
