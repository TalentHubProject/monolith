package org.talenthub.module.xp.task;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import org.talenthub.service.MessageBroadcasterService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class ActivityCalculTask implements Runnable {

    @Getter
    private static ActivityCalculTask instance;

    private final Guild guild;
    private final MessageBroadcasterService messageBroadcasterService;

    private final Map<Integer, Integer> messagesCountPerHour = new HashMap<>();
    private int messageCountThisHour = 0;

    @Getter
    private boolean boostActivated = false;

    public ActivityCalculTask(Guild guild, MessageBroadcasterService messageBroadcasterService) {
        this.guild = guild;
        this.messageBroadcasterService = messageBroadcasterService;
    }

    @Override
    public void run() {
        calculateAndCleanup();
    }

    private synchronized void calculateAndCleanup() {
        int total = messagesCountPerHour.values().stream().mapToInt(Integer::intValue).sum();
        int average = total / messagesCountPerHour.size();

        if (average < messageCountThisHour) {
            activateBoost();
        } else if (boostActivated) {
            deactivateBoost();
        }

        updateMap();
    }

    private void updateMap() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Paris"));
        messagesCountPerHour.put(now.getHour(), messageCountThisHour);
        messageCountThisHour = 0;
    }

    public synchronized void incrementMessage() {
        messageCountThisHour++;
    }

    private void activateBoost() {
        boostActivated = true;
        messageBroadcasterService.broadcastBasicMessageEmbed(guild.getDefaultChannel().asStandardGuildMessageChannel(), "Le boost d'activité est activé");
    }

    private void deactivateBoost() {
        boostActivated = false;
        messageBroadcasterService.broadcastBasicMessageEmbed(guild.getDefaultChannel().asStandardGuildMessageChannel(), "Le boost d'activité est désactivé");
    }

}
