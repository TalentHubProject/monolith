package org.talenthub.module.xp.listener;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.talenthub.module.xp.service.LevelService;
import org.talenthub.module.xp.task.ActivityCalculTask;
import org.talenthub.module.xp.task.ActivityCalculTaskFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class ReadyListener extends ListenerAdapter {

    private final LevelService levelService;
    private final Logger LOGGER = LoggerFactory.getLogger(ReadyListener.class);

    @Override
    public void onReady(final ReadyEvent event) {

        levelService.checkAndGenerateFirsLevel();

    }

    @Override
    public void onGuildReady(final GuildReadyEvent event) {

        ActivityCalculTask activityCalculTask = new ActivityCalculTaskFactory().create(event.getGuild());

        try (ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1)) {

            scheduler.scheduleAtFixedRate(activityCalculTask, 0, 1, TimeUnit.HOURS);

        }catch (Exception e){

            LOGGER.error("Error while scheduling ActivityCalculTask", e);

        }

    }
}
