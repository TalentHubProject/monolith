package org.talenthub.module.embed.dba.command;

import fr.leonarddoo.dba.annotation.Command;
import fr.leonarddoo.dba.annotation.Option;
import fr.leonarddoo.dba.annotation.Options;
import fr.leonarddoo.dba.element.DBACommand;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.springframework.stereotype.Component;
import org.talenthub.service.ConfigService;

import java.awt.*;
import java.util.Objects;

@Command(name = "informational-message", description = "Créer un message d'information.")
@Options({
        @Option(type = OptionType.STRING, name = "title", description = "Titre du message.", required = true),
        @Option(type = OptionType.STRING, name = "description", description = "Description du message.", required = true)
})
@Component
@AllArgsConstructor
public class InformationalMessageCmd implements DBACommand {

    private final ConfigService configService;

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        final var title = Objects.requireNonNull(event.getOption("title")).getAsString();
        final var description = Objects.requireNonNull(event.getOption("description")).getAsString();

        event.getChannel().asTextChannel().sendMessageEmbeds(new EmbedBuilder()
                        .setTitle(title)
                .setColor(Color.decode(configService.getString("color-code")))
                .setDescription(description)
                .build()).queue();

    }
}
