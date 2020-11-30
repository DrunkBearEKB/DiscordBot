package discord.eventListeners;

import discord.DsBot;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.update.GenericGuildUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.*;

public class DsEventListenerMain extends ListenerAdapter {
    private final DsBot bot;

    public DsEventListenerMain(DsBot bot) {
        this.bot = bot;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        Date date = new Date(System.currentTimeMillis());

        try {
            utils.Functions.createSystemTray("DiscordBot successfully started!", "Discord");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        for (Guild guild: event.getJDA().getGuilds()) {
            this.bot.putBooleanInDictionaryBotJoined(guild, false);
            this.bot.putAudioManagerInDictionary(guild, guild.getAudioManager());
        }

        System.out.printf("[STARTED] %s\n", date);
    }

    @Override
    public void onGenericGuildUpdate(@Nonnull GenericGuildUpdateEvent event) {
        this.bot.putBooleanInDictionaryBotJoined(event.getGuild(), false);
        this.bot.putAudioManagerInDictionary(event.getGuild(), event.getGuild().getAudioManager());
    }
}
