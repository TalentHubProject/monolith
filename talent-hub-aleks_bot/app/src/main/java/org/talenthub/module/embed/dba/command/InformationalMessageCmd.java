package org.talenthub.module.embed.dba.command;

import fr.leonarddoo.dba.annotation.Command;
import fr.leonarddoo.dba.annotation.Option;
import fr.leonarddoo.dba.annotation.Options;
import fr.leonarddoo.dba.element.DBACommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.springframework.stereotype.Component;

@Command(name = "informational-message", description = "Créer un message d'information.")
@Options({
        @Option(type = OptionType.STRING, name = "title", description = "Titre du message.", required = true),
        @Option(type = OptionType.STRING, name = "description", description = "Description du message.", required = true)
})
@Component
public class InformationalMessageCmd implements DBACommand {

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        String title = event.getOption("title").getAsString();
        String description = event.getOption("description").getAsString();

        event.getChannel().asTextChannel().sendMessageEmbeds(new EmbedBuilder()
                        .setTitle(title)
                        .setDescription(description)
                .build()).queue();

    }
}
