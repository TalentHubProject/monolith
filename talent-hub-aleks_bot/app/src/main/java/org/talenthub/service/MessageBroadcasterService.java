package org.talenthub.service;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@AllArgsConstructor
public class MessageBroadcasterService {

    private final ConfigService configService;

    public void broadcastMessage(final TextChannel channel, final String message){
        channel.sendMessage(message).queue();
    }

    public void broadcastBasicMessageEmbed(final GuildMessageChannel channel, final String message){
        channel.sendMessageEmbeds(new EmbedBuilder()
                .setColor(Color.decode(configService.getString("color-code")))
                .setDescription(message)
                .build()).queue();
    }

    public void broadcastMessageEmbed(final TextChannel channel, final MessageEmbed embed){
        channel.sendMessageEmbeds(embed).queue();
    }

}
