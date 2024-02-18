package org.talenthub.tools;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

@SuppressWarnings("unused")
public class EmbedTemplates {

    /**
     * Create a success embed with a description
     * @param description The description of the embed
     * @return Success embed
     */
    public static MessageEmbed successEmbed(String description){
        return new EmbedBuilder()
                .setColor(Color.GREEN)
                .setDescription(description)
                .build();
    }

    /**
     * Create an error embed with a description
     * @param description The description of the embed
     * @return Error embed
     */
    public static MessageEmbed errorEmbed(String description){
        return new EmbedBuilder()
                .setColor(Color.RED)
                .setDescription(description)
                .build();
    }
}
