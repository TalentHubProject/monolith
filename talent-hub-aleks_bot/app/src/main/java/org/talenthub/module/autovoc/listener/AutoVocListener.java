package org.talenthub.module.autovoc.listener;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.talenthub.service.ConfigService;

@Component
@AllArgsConstructor
public class AutoVocListener extends ListenerAdapter {

    private final ConfigService configService;
    private final Logger LOGGER = LoggerFactory.getLogger(AutoVocListener.class);

    @Override
    public void onReady(ReadyEvent event) {
        Category category = event.getJDA().getCategoryById(configService.getString("auto-voc-category-id"));

        if(category == null){
            LOGGER.error("AutoVoc category not found");
            return;
        }

        category.getVoiceChannels().forEach(voice -> {
            if(!voice.getId().equals(configService.getString("auto-voc-channel-id"))){
                voice.delete().queue();
            }
        });

    }
}
