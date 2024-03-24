package org.talenthub.module.study;

import fr.leonarddoo.dba.annotation.Command;
import fr.leonarddoo.dba.annotation.Option;
import fr.leonarddoo.dba.annotation.Options;
import fr.leonarddoo.dba.element.DBACommand;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Command(name = "study-timer", description = "Créer un timer pour étudier.")
@Options({@Option(type = OptionType.STRING, name = "title", description = "Titre du timer.", required = true),
        @Option(type = OptionType.STRING, name = "description", description = "Description du timer.", required = true),
        @Option(type = OptionType.STRING, name = "time", description = "Durée du timer, ex: 5m30s.", required = true)})
@Component
@AllArgsConstructor
public class StudyTimerCmd implements DBACommand {

    /**
     * Execute the command
     *
     * @param event Event
     */
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String title = Objects.requireNonNull(event.getOption("title"), "Le titre est obligatoire.").getAsString();
        String description = Objects.requireNonNull(event.getOption("description"), "La description est obligatoire.").getAsString();
        String timeString = Objects.requireNonNull(event.getOption("time"), "La durée est obligatoire.").getAsString();

        Duration duration = parseDuration(timeString);
        if (duration == null) {
            event.reply("Format de durée invalide. Veuillez utiliser le format 'XmYs', par exemple '5m30s'.").queue();
            return;
        }

        long milliseconds = duration.toMillis();
        event.reply("Timer créé pour étudier :\n**" + title + "**\n" + description + "\nDurée : " + formatDuration(duration))
                .queue(message ->
                        message.editOriginal("Timer terminé pour :\n**" + title + "**\n" + description)
                                .queueAfter(milliseconds, TimeUnit.MILLISECONDS));
    }

    private Duration parseDuration(String timeString) {
        try {
            return Duration.parse("PT" + timeString.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    private String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        seconds %= 60;
        return minutes + "m" + seconds + "s";
    }
}