package org.talenthub.module.study;

import fr.leonarddoo.dba.annotation.Command;
import fr.leonarddoo.dba.annotation.Option;
import fr.leonarddoo.dba.annotation.Options;
import fr.leonarddoo.dba.element.DBACommand;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Command(name = "study-timer", description = "Créer un timer pour étudier.")
@Options({@Option(type = OptionType.STRING, name = "title", description = "Titre du timer.", required = true),
        @Option(type = OptionType.STRING, name = "description", description = "Description du timer.", required = true),
        @Option(type = OptionType.STRING, name = "time", description = "Durée du timer, ex: 5m30s.", required = true)})
@Component
@AllArgsConstructor
public class StudyTimerCmd implements DBACommand {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

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

        long totalSeconds = duration.getSeconds();
        AtomicLong remainingSeconds = new AtomicLong(totalSeconds);

        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("⏰ Timer d'étude").setDescription("**" + title + "**\n" + description).setColor(Color.BLUE).setFooter("Temps restant : " + formatDuration(duration));

        event.replyEmbeds(embedBuilder.build()).queue(message -> {
            executor.scheduleAtFixedRate(() -> {
                remainingSeconds.getAndDecrement();
                if (remainingSeconds.get() <= 0) {
                    embedBuilder.setColor(Color.GREEN).setFooter("Temps écoulé !").setTimestamp(message.getTimeCreated().plusSeconds(totalSeconds).toInstant());
                    event.getHook().editOriginalEmbeds(embedBuilder.build()).queue();
                    executor.shutdown();
                } else {
                    embedBuilder.setFooter("Temps restant : " + formatDuration(Duration.ofSeconds(remainingSeconds.get())));
                    event.getHook().editOriginalEmbeds(embedBuilder.build()).queue();
                }
            }, 1, 1, TimeUnit.SECONDS);
        });
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
        return String.format("%02d:%02d", minutes, seconds);
    }
}