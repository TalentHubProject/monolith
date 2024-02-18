package org.talenthub.module.xp.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WishWelcome {

    private final long welcomeMessageId;

    private final long welcomeUserID;

    private final List<Long> responseUserIds = new ArrayList<>();

    public WishWelcome(final long welcomeMessageId, final long welcomeUserID) {
        this.welcomeMessageId = welcomeMessageId;
        this.welcomeUserID = welcomeUserID;
    }

}
