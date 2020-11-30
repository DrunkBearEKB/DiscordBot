package discord;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import discord.eventListeners.DsEventListenerMain;
import discord.eventListeners.DsEventListenerTextChannels;
import discord.eventListeners.DsEventListenerVoiceChannels;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Map;

public class DsBot {
    // ================================================================================
    // Flags for discord bot
    private boolean flagLogging = false;
    private boolean flagTrayCreators = false;

    private boolean flagTrayGuildMessage = false;
    private boolean flagTrayPrivateMessage = false;
    private boolean flagTrayGuildVoiceChannelJoin = false;
    private boolean flagTrayGuildVoiceChannelMove = false;
    private boolean flagTrayGuildVoiceChannelLeave = false;

    private final Map<Guild, AudioManager> dictionaryAudioManagers;
    private final Map<Guild, Boolean> dictionaryBotJoinedVoiceChannels;

    // ================================================================================
    public DsBot(String token, Map<String, Boolean> flags) {
        JDA jda;
        try {
            jda = JDABuilder.createDefault(token)
                    .setAutoReconnect(true)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .setActivity(Activity.playing("!help"))
                    .build();

            jda.addEventListener(
                    new DsEventListenerMain(this),
                    new DsEventListenerTextChannels(this),
                    new DsEventListenerVoiceChannels(this));
        } catch (LoginException e) {
            e.printStackTrace();
        }

        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);

        // ================================================================================
        // Flags for discord bot
        if (flags.containsKey("flagLogging"))
            this.flagLogging = flags.get("flagLogging");
        if (flags.containsKey("flagTrayCreators"))
            this.flagTrayCreators = flags.get("flagTrayCreators");

        if (flags.containsKey("flagTrayGuildMessage"))
            this.flagTrayGuildMessage = flags.get("flagTrayGuildMessage");
        if (flags.containsKey("flagTrayPrivateMessage"))
            this.flagTrayPrivateMessage = flags.get("flagTrayPrivateMessage");
        if (flags.containsKey("flagTrayGuildVoiceChannelJoin"))
            this.flagTrayGuildVoiceChannelJoin = flags.get("flagTrayGuildVoiceChannelJoin");
        if (flags.containsKey("flagTrayGuildVoiceChannelMove"))
            this.flagTrayGuildVoiceChannelMove = flags.get("flagTrayGuildVoiceChannelMove");
        if (flags.containsKey("flagTrayGuildVoiceChannelLeave"))
            this.flagTrayGuildVoiceChannelLeave = flags.get("flagTrayGuildVoiceChannelLeave");

        this.dictionaryAudioManagers = new Hashtable<>();
        this.dictionaryBotJoinedVoiceChannels = new IdentityHashMap<>();
    }

    public char getSymbolCommand() {
        return '!';
    }

    public String getCreators() {
        String result = "";
        result += "405767026422972416";
        result += " ";
        result += "654326469966823431";

        return result;
    }

    public boolean getFlagLogging() {
        return this.flagLogging;
    }

    public boolean getFlagTrayCreators() {
        return this.flagTrayCreators;
    }

    public boolean getFlagTrayPrivateMessage() {
        return this.flagTrayPrivateMessage;
    }

    public boolean getFlagTrayGuildMessage() {
        return this.flagTrayGuildMessage;
    }

    public boolean getFlagTrayGuildVoiceChannelJoin() {
        return this.flagTrayGuildVoiceChannelJoin;
    }

    public boolean getFlagTrayGuildVoiceChannelMove() {
        return this.flagTrayGuildVoiceChannelMove;
    }

    public boolean getFlagTrayGuildVoiceChannelLeave() {
        return this.flagTrayGuildVoiceChannelLeave;
    }

    public AudioManager getAudioManagerByGuild(Guild guild) {
        return this.dictionaryAudioManagers.get(guild);
    }

    public void putAudioManagerInDictionary(Guild guild, AudioManager audioManager) {
        this.dictionaryAudioManagers.put(guild, audioManager);
    }

    public Boolean checkBotJoinedVoiceChannelByGuild(Guild guild) {
        return this.dictionaryBotJoinedVoiceChannels.get(guild);
    }

    public void putBooleanInDictionaryBotJoined(Guild guild, Boolean value) {
        this.dictionaryBotJoinedVoiceChannels.put(guild, value);
    }

    public void replaceBooleanInDictionaryBotJoined(Guild guild, Boolean value) {
        this.dictionaryBotJoinedVoiceChannels.replace(guild, value);
    }
}
