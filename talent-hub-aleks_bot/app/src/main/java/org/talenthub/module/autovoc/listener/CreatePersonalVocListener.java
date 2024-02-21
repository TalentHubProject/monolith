package org.talenthub.module.autovoc.listener;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.talenthub.module.autovoc.model.PersonalVocal;
import org.talenthub.service.ConfigService;

@Component
@AllArgsConstructor
public class CreatePersonalVocListener extends ListenerAdapter {

    private final ConfigService configService;
    private final Logger LOGGER = LoggerFactory.getLogger(CreatePersonalVocListener.class);

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {

        if(event.getChannelJoined() == null) return;
        if(!event.getChannelJoined().getId().equals(configService.getString("auto-voc-channel-id"))) return;
        if(PersonalVocal.vocalMap.containsKey(event.getMember().getIdLong())) return;

        Category category = event.getGuild().getCategoryById(configService.getString("auto-voc-category-id"));

        if(category == null){
            LOGGER.error("AutoVoc category not found");
            return;
        }

        category.createVoiceChannel(event.getMember().getEffectiveName())
                .addMemberPermissionOverride(event.getMember().getIdLong(), Permission.MANAGE_CHANNEL.getRawValue(), Permission.UNKNOWN.getRawValue()).queue(voice -> {
                    event.getGuild().moveVoiceMember(event.getMember(), voice).queue();
                    PersonalVocal.vocalMap.put(event.getMember().getIdLong(), voice.getIdLong());
                });

    }
}
