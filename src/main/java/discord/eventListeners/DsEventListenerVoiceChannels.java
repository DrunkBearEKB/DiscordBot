package discord.eventListeners;

import discord.DsBot;
import utils.LogMessage;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class DsEventListenerVoiceChannels extends ListenerAdapter {
    private final DsBot bot;

    public DsEventListenerVoiceChannels(DsBot bot) {
        this.bot = bot;
    }

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        if (this.bot.getFlagTrayGuildVoiceChannelJoin()) {
            if (!this.bot.getFlagTrayCreators() &&
                    this.bot.getCreators().contains(event.getMember().getId()))
                return;

            String guild = event.getGuild().getName();
            String voiceChannel = event.getChannelJoined().getName();
            String memberName = event.getMember().getNickname();

            String message = memberName + " joined [" + guild + " / " + voiceChannel + "]";
            try {
                utils.Functions.createSystemTray(message, "Discord");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent event) {
        if (this.bot.getFlagTrayGuildVoiceChannelMove()) {
            if (!this.bot.getFlagTrayCreators() &&
                    this.bot.getCreators().contains(event.getMember().getId()))
                return;

            String guild = event.getGuild().getName();
            String voiceChannel = event.getChannelJoined().getName();
            String memberName = event.getMember().getNickname();

            String message = memberName + " moved to [" + guild + " / " + voiceChannel + "]";
            try {
                utils.Functions.createSystemTray(message, "Discord");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        if (this.bot.getFlagTrayGuildVoiceChannelLeave()) {
            if (!this.bot.getFlagTrayCreators() &&
                    this.bot.getCreators().contains(event.getMember().getId()))
                return;

            String guild = event.getGuild().getName();
            String voiceChannel = event.getChannelLeft().getName();
            String memberName = event.getMember().getNickname();

            String message = memberName + " left [" + guild + " / " + voiceChannel + "]";
            try {
                utils.Functions.createSystemTray(message, "Discord");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void onGuildVoiceUpdate(@Nonnull GuildVoiceUpdateEvent event) {
        if (this.bot.getFlagLogging()) {
            LogMessage message = new LogMessage(event.getEntity().getGuild().getName());
            message.addMessagePart("UserNickName", event.getEntity().getNickname());

            if (event.getChannelJoined() != null) {
                message.addMessagePart("ChannelJoined", event.getChannelJoined().getName());
                message.addMessagePart("ChannelLeft", "null");
            }
            else if (event.getChannelLeft() != null) {
                message.addMessagePart("ChannelJoined", "null");
                message.addMessagePart("ChannelLeft", event.getChannelLeft().getName());
            }

            System.out.println(message.ToString());
        }
    }
}
