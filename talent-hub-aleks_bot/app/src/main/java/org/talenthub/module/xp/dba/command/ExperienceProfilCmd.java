package org.talenthub.module.xp.dba.command;

import fr.leonarddoo.dba.annotation.Command;
import fr.leonarddoo.dba.annotation.Option;
import fr.leonarddoo.dba.element.DBACommand;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.springframework.stereotype.Component;
import org.talenthub.module.xp.service.api.CreatureAPIService;

@Command(name = "experience-profil", description = "Command to get the experience profil of a user")
@Option(type = OptionType.USER, name = "user", description = "The user to get the experience profil")
@Component
@AllArgsConstructor
public class ExperienceProfilCmd implements DBACommand {

    private final CreatureAPIService creatureAPIService;

    @Override
    public void execute(final SlashCommandInteractionEvent event) {

        User user;
        if(event.getOption("user") != null){
            user = event.getOption("user").getAsUser();
        } else {
            user = event.getUser();
        }

        creatureAPIService.getRandomRace();

    }


}
