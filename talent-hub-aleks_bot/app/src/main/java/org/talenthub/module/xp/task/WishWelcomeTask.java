package org.talenthub.module.xp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talenthub.module.xp.model.WishWelcome;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WishWelcomeTask implements Runnable {

    private static final Map<Long, WishWelcome> welcomeMap = new HashMap<>();

    private final Logger LOGGER = LoggerFactory.getLogger(WishWelcomeTask.class);

    private final long messageId;

    public WishWelcomeTask(final long messageId) {
        this.messageId = messageId;
    }

    @Override
    public void run() {
        welcomeMap.remove(messageId);
    }

    public void execute(WishWelcome wishWelcome){
        welcomeMap.put(messageId, wishWelcome);
        // Créez un ScheduledExecutorService avec un seul thread
        try (ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor()) {
            // Planifiez la tâche pour 5 minutes
            executor.schedule(new WishWelcomeTask(messageId), 5, TimeUnit.MINUTES);
        }catch (Exception e){
            LOGGER.error("Error while scheduling the welcome message", e);
        }
    }

    public static WishWelcome getWishWelcome(final long messageId){
        return welcomeMap.get(messageId);
    }
}
