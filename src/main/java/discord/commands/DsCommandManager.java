package discord.commands;

import discord.DsBot;
import discord.commands.executors.*;
import discord.commands.executors.DsCommandExecutorOthers;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import javax.annotation.Nonnull;

public class DsCommandManager {
    private final DsCommandExecutorAdministrative commandExecutorAdministrative;
    private final DsCommandExecutorOthers commandExecutorOthers;
    private final DsCommandExecutorSecret commandExecutorSecret;
    private final DsCommandExecutorVoice commandExecutorVoice;

    public DsCommandManager(DsBot bot) {
        this.commandExecutorAdministrative = new DsCommandExecutorAdministrative();
        this.commandExecutorOthers = new DsCommandExecutorOthers(bot);
        this.commandExecutorSecret = new DsCommandExecutorSecret(bot);
        this.commandExecutorVoice = new DsCommandExecutorVoice();
    }

    public String onGuildCommand(@Nonnull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();
        String[] commandParsed = message.substring(1).split(" ", -1);
        String command = commandParsed[0];

        boolean result;
        String errorMessage = "";

        switch (command) {
            //Административные команды
            case "ban":
                result = this.commandExecutorAdministrative.ban(event);
                if (!result) {
                    errorMessage = "Can't ban members!";
                }
                break;
            case "giveRoles":
                result = this.commandExecutorAdministrative.giveRoles(event);
                if (!result) {
                    errorMessage = "Can't give roles to members!";
                }
                break;
            case "removeRoles":
                result = this.commandExecutorAdministrative.removeRoles(event);
                if (!result) {
                    errorMessage = "Can't remove roles to members!";
                }
                break;
            case "addVoiceChannel":
                result = this.commandExecutorAdministrative.addVoiceChannel(event);
                if (!result) {
                    errorMessage = "Can't add voice channel!";
                }
                break;
            case "moveVoiceChannel":
                result = this.commandExecutorAdministrative.moveVoiceChannel(event);
                if (!result) {
                    errorMessage = "Can't move voice channel!";
                }
                break;
            case "removeVoiceChannel":
                result = this.commandExecutorAdministrative.removeVoiceChannel(event);
                if (!result) {
                    errorMessage = "Can't remove voice channel!";
                }
                break;
            case "addTextChannel":
                result = this.commandExecutorAdministrative.addTextChannel(event);
                if (!result) {
                    errorMessage = "Can't add text channel!";
                }
                break;
            case "moveTextChannel":
                result = this.commandExecutorAdministrative.moveTextChannel(event);
                if (!result) {
                    errorMessage = "Can't move text channel!";
                }
                break;
            case "removeTextChannel":
                result = this.commandExecutorAdministrative.removeTextChannel(event);
                if (!result) {
                    errorMessage = "Can't remove text channel!";
                }
                break;

            // Команды для управления голосовыми каналами
            case "kick":
                result = this.commandExecutorVoice.kick(event);
                if (!result) {
                    errorMessage = "Can't kick members!";
                }
                break;
            case "mute":
                result = this.commandExecutorVoice.mute(event);
                if (!result) {
                    errorMessage = "Can't mute members!";
                }
                break;
            case "unmute":
                result = this.commandExecutorVoice.unmute(event);
                if (!result) {
                    errorMessage = "Can't unmute members!";
                }
                break;
            case "move":
                result = this.commandExecutorVoice.move(event, commandParsed);
                if (!result) {
                    errorMessage = "Can't move members";
                }
                break;
            case "afk":
                result = this.commandExecutorVoice.afk(event);
                if (!result) {
                    errorMessage = "Can't move members!";
                }
                break;

            // Общие
            case "clear":
                result = this.commandExecutorOthers.clear(event, commandParsed);
                if (!result) {
                    errorMessage = "Can't clear messages!";
                }
                break;
            case "translate":
                result = this.commandExecutorOthers.translate(event);
                if (!result) {
                    errorMessage = "Can't translate message!";
                }
                break;
            case "prediction":
                result = this.commandExecutorOthers.prediction(event);
                if (!result) {
                    errorMessage = "Can't send predictions!";
                }
                break;
            case "meme":
                result = this.commandExecutorOthers.meme(event);
                if (!result) {
                    errorMessage = "Can't send meme!";
                }
                break;
            case "help":
                result = this.commandExecutorOthers.help(event);
                if (!result) {
                    errorMessage = "Can't print help!";
                }
                break;

            // Скрытые
            case "send":
                result = this.commandExecutorSecret.send(event, commandParsed);
                if (!result) {
                    errorMessage = "Can't print help!";
                }
                break;
        }

        return errorMessage;
    }

    public String onPrivateCommand(@Nonnull PrivateMessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();
        String[] commandParsed = message.substring(1).split(" ", -1);
        String command = commandParsed[0];

        boolean result;
        String errorMessage = "";

        switch (command) {
            case "translate":
                result = this.commandExecutorOthers.translate(event);
                if (!result) {
                    errorMessage = "Can't translate message!";
                }
                break;
            case "prediction":
                result = this.commandExecutorOthers.prediction(event);
                if (!result) {
                    errorMessage = "Can't send predictions!";
                }
                break;
            case "meme":
                result = this.commandExecutorOthers.meme(event);
                if (!result) {
                    errorMessage = "Can't send meme!";
                }
                break;
            case "help":
                result = this.commandExecutorOthers.help(event);
                if (!result) {
                    errorMessage = "Can't print help!";
                }
                break;
            case "send":
                result = this.commandExecutorSecret.send(event, commandParsed);
                if (!result) {
                    errorMessage = "Can't send private message to user!";
                }
                break;
        }

        return errorMessage;
    }

    public String onReactionAdd(@Nonnull MessageReactionAddEvent event) {
        boolean result;
        String errorMessage = "";

        System.out.println(event.getReactionEmote().getEmoji());

        if (event.getReactionEmote().getEmoji().equals("\uD83C\uDDF7\uD83C\uDDFA") ||
                event.getReactionEmote().getEmoji().equals("\uD83C\uDDEC\uD83C\uDDE7")) {
            result = this.commandExecutorOthers.translate(event);
            if (!result) {
                errorMessage = "Can't translate message!";
            }
        }

        return errorMessage;
    }
}
