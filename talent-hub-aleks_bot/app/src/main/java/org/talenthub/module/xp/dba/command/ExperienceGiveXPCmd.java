package org.talenthub.module.xp.dba.command;

import fr.leonarddoo.dba.annotation.Command;
import fr.leonarddoo.dba.annotation.Option;
import fr.leonarddoo.dba.annotation.Options;
import fr.leonarddoo.dba.element.DBACommand;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.springframework.stereotype.Component;
import org.talenthub.module.xp.service.LevelService;
import org.talenthub.tools.EmbedTemplates;

@Command(name = "experience-give-xp", description = "Donner de l'xp à un membre.")
@Options({
        @Option(type = OptionType.USER, name = "user", description = "Le joueur à qui donner l'xp.", required = true),
        @Option(type = OptionType.INTEGER, name = "xp", description = "Le nombre d'xp à donner.", required = true)
})
@Component
@AllArgsConstructor
public class ExperienceGiveXPCmd implements DBACommand {

    private final LevelService levelService;

    @Override
    public void execute(final SlashCommandInteractionEvent event) {

        if(!event.getMember().hasPermission(Permission.ADMINISTRATOR)){
            event.replyEmbeds(EmbedTemplates.errorEmbed("Vous n'avez pas la permission d'utiliser cette commande.")).queue();
            return;
        }

        event.deferReply(true).queue();

        Member member = event.getOption("user").getAsMember();
        int xp = (int) event.getOption("xp").getAsLong();

        levelService.addXp(event.getChannel().asGuildMessageChannel(), member, xp);

        event.getHook().editOriginalEmbeds(EmbedTemplates.successEmbed("Vous avez donné " + xp + " xp à " + member.getEffectiveName() + ".")).queue();

    }
}
