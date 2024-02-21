package org.talenthub.module.xp.task;

import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.talenthub.service.MessageBroadcasterService;

@Component
@NoArgsConstructor
public class ActivityCalculTaskFactory {

    private MessageBroadcasterService messageBroadcasterService;

    @Autowired
    public ActivityCalculTaskFactory(MessageBroadcasterService messageBroadcasterService) {
        this.messageBroadcasterService = messageBroadcasterService;
    }

    public ActivityCalculTask create(Guild guild) {
        return new ActivityCalculTask(guild, messageBroadcasterService);
    }

}
