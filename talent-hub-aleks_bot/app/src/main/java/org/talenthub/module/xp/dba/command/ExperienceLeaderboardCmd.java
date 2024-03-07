package org.talenthub.module.xp.dba.command;

import fr.leonarddoo.dba.annotation.Command;
import fr.leonarddoo.dba.element.DBACommand;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;
import org.talenthub.module.xp.entity.PlayerLevel;
import org.talenthub.module.xp.repository.PlayerLevelRepository;
import org.talenthub.service.ConfigService;

import java.awt.*;
import java.util.List;

@Command(name = "experience-leaderboard", description = "Afficher le classement des joueurs avec le plus d'expérience")
@Component
@AllArgsConstructor
public class ExperienceLeaderboardCmd implements DBACommand {

    private final PlayerLevelRepository playerLevelRepository;
    private final ConfigService configService;

    @Override
    public void execute(final SlashCommandInteractionEvent event) {

        event.deferReply(true).queue();

        List<PlayerLevel> top10 = playerLevelRepository.findTop10ByOrderByExperienceDesc();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < top10.size(); i++) {
            sb.append(i + 1).append(". <@").append(top10.get(i).getDiscordId()).append("> - ").append(top10.get(i).getXp()).append(" XP\n");
        }

        event.getHook().editOriginalEmbeds(new EmbedBuilder()
                        .setTitle("Leaderboard de l'experience")
                        .setThumbnail(event.getGuild().getIconUrl())
                        .setDescription(sb.toString())
                        .setColor(Color.decode(configService.getString("color-code")))

                .build()).queue();


    }
}
