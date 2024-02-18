package org.talenthub.tools;

import lombok.Getter;
import net.dv8tion.jda.api.entities.emoji.Emoji;

@Getter
@SuppressWarnings("unused")
public enum EmojiLibrary {

    SUCCESS(""),
    ;

    private final Emoji symbol;

    EmojiLibrary(String unicode) {
        this.symbol = Emoji.fromUnicode(unicode);
    }

}
