package org.talenthub.module.moderation.listener;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import org.talenthub.tools.EmbedTemplates;

@Component
public class AntiPubListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {

        if(!event.getMessage().getContentRaw().contains("discord.gg/")) return;
        if(event.getAuthor().isBot()) return;
        if(event.getMember().hasPermission(Permission.ADMINISTRATOR)) return;
        if(event.getMessage().getContentRaw().contains(event.getGuild().getVanityUrl())) return;

        event.getGuild().retrieveInvites().queue(invites -> {
            for(Invite invite : invites) {
                if(event.getMessage().getContentRaw().contains(invite.getUrl())) {
                    return;
                }
            }
        });

        event.getMessage().replyEmbeds(EmbedTemplates.errorEmbed("Votre message a été supprimé car une pub a été detecté.")).queue(reply ->
                event.getMessage().delete().queue());

    }
}
