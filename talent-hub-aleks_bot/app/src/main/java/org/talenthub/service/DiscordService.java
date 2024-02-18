package org.talenthub.service;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class DiscordService {

    @Autowired
    public DiscordService(
            List<EventListener> listeners,
            ConfigService configService) throws FileNotFoundException, IOException {

        final var configFile = new ClassPathResource("config.json").getFile();
        configService.load(configFile);

        final var token = System.getenv("TOKEN");
        if (token == null) {
            throw new FileNotFoundException("TOKEN environment variable not found");
        }

        JDABuilder.createLight(token,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.GUILD_PRESENCES)
                .addEventListeners(listeners.toArray())
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableCache(CacheFlag.VOICE_STATE)
                .build();
    }

}

