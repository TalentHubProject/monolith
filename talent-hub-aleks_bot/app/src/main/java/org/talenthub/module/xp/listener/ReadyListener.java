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

import java.util.List;
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
        levelService.checkAndGenerateFirstLevel();
    }

    @Override
    public void onGuildReady(final GuildReadyEvent event) {
        ActivityCalculTask activityCalculTask = new ActivityCalculTaskFactory().create(event.getGuild());
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(activityCalculTask, 0, 1, TimeUnit.HOURS);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                scheduler.shutdown();
                if (!scheduler.awaitTermination(1, TimeUnit.MINUTES)) {
                    LOGGER.error("Executor did not terminate in the specified time.");
                    List<Runnable> droppedTasks = scheduler.shutdownNow();
                    LOGGER.error("Executor was abruptly shut down. {} tasks will not be executed.", droppedTasks.size());
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }));
    }
}
