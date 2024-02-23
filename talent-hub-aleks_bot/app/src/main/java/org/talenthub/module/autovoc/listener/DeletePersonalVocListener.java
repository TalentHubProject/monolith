package org.talenthub.module.autovoc.listener;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import org.talenthub.module.autovoc.model.PersonalVocal;

@Component
public class DeletePersonalVocListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {

        if(event.getChannelLeft() == null) return;
        if(!event.getChannelLeft().getMembers().isEmpty()) return;
        if(!PersonalVocal.vocalMap.containsKey(event.getMember().getIdLong())) return;

        PersonalVocal.vocalMap.remove(event.getMember().getIdLong());
        event.getChannelLeft().delete().queue();

    }
}
