package org.talenthub.module.xp.dba.command;

import fr.leonarddoo.dba.annotation.Command;
import fr.leonarddoo.dba.annotation.Option;
import fr.leonarddoo.dba.element.DBACommand;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.utils.FileUpload;
import org.springframework.stereotype.Component;
import org.talenthub.module.xp.entity.PlayerLevel;
import org.talenthub.module.xp.service.PlayerLevelService;
import org.talenthub.module.xp.service.api.CreatureAPIService;

import java.io.File;

@Command(name = "experience-profil", description = "Command to get the experience profil of a user")
@Option(type = OptionType.USER, name = "user", description = "The user to get the experience profil")
@Component
@AllArgsConstructor
public class ExperienceProfilCmd implements DBACommand {

    private final PlayerLevelService playerLevelService;

    @Override
    public void execute(final SlashCommandInteractionEvent event) {

        event.deferReply().queue();

        Member member;
        if(event.getOption("member") != null){
            member = event.getOption("member").getAsMember();
        } else {
            member = event.getMember();
        }

        PlayerLevel playerLevel = playerLevelService.getPlayerLevel(member.getIdLong());
        File imageFile = playerLevelService.getCreature(playerLevel);

        event.getChannel().sendFiles(FileUpload.fromData(imageFile)).queue(message -> {
            String imageUrl = message.getAttachments().get(0).getUrl();

            // Mettez à jour l'embed avec l'URL de l'image
            event.getHook().editOriginalEmbeds(new EmbedBuilder()
                            .setTitle("Profil de "+member.getEffectiveName())
                            .addField("Niveau", String.valueOf(playerLevel.getLevel().getId()), true)
                            .addField("XP", String.valueOf(playerLevel.getXp()), true)
                            .addField("XP restant", String.valueOf(playerLevel.getLevel().getMaxXp() - playerLevel.getXp()), true)
                            .setImage(imageUrl)
                    .build()).queue(success ->
                        message.delete().queue());
        });


    }


}
