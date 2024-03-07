package org.talenthub.listener;

import fr.leonarddoo.dba.element.*;
import fr.leonarddoo.dba.loader.DBALoader;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.talenthub.service.ConfigService;

import java.util.ArrayList;
import java.util.List;

@Component
public class Setup extends ListenerAdapter {

    private final List<DBACommand> commands;
    private final List<DBAButton> buttons;
    private final List<DBAStringSelectMenu> stringSelectMenus;
    private final List<DBAEntitySelectMenu> entitySelectMenus;
    private final List<DBAModal> modals;
    private final ConfigService configService;

    @Autowired
    public Setup(List<DBACommand> commands, List<DBAButton> buttons, List<DBAStringSelectMenu> stringSelectMenus, List<DBAEntitySelectMenu> entitySelectMenus, List<DBAModal> modals, ConfigService configService) {
        this.commands = commands;
        this.buttons = buttons;
        this.stringSelectMenus = stringSelectMenus;
        this.entitySelectMenus = entitySelectMenus;
        this.modals = modals;
        this.configService = configService;
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        event.getGuild().updateCommands().queue();
        DBALoader.getInstance(event.getJDA()).addDBACommandsToGuild(event.getGuild(),
                this.commands.toArray()
        );
    }

    @Override
    public void onReady(ReadyEvent event) {
        List<DBAEvent> events = new ArrayList<>();
        events.addAll(this.buttons);
        events.addAll(this.stringSelectMenus);
        events.addAll(this.entitySelectMenus);
        events.addAll(this.modals);
        DBALoader.getInstance(event.getJDA()).initDBAEvent(
                events.toArray()
        );
    }
}
