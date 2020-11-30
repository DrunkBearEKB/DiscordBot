package discord.eventListeners;

import discord.DsBot;
import discord.commands.DsCommandManager;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import utils.LogMessage;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.vdurmont.emoji.EmojiParser;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class DsEventListenerTextChannels extends ListenerAdapter {
    private final DsBot bot;
    private final DsCommandManager commandManager;

    public DsEventListenerTextChannels(DsBot bot) {
        this.bot = bot;
        this.commandManager = new DsCommandManager(bot);
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getAuthor() == event.getGuild().getSelfMember().getUser())
            return;

        String guild = event.getGuild().getName();
        String textChannel = event.getChannel().getName();
        String author = event.getAuthor().getName();
        String message = event.getMessage().getContentDisplay();

        this.onGuildMessageSpecialized(event);

        if (message.length() != 0 && message.charAt(0) == this.bot.getSymbolCommand()) {
            try {
                String result = commandManager.onGuildCommand(event);
                if (!result.equals(" ")) {
                    System.out.println(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (this.bot.getFlagTrayGuildMessage()) {
            String text = "[" + guild + " / " + textChannel + "] " + author + ": \"" + message + "\"";
            try {
                utils.Functions.createSystemTray(text, "Discord");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();

        if (message.charAt(0) == this.bot.getSymbolCommand()) {
            String result = this.commandManager.onPrivateCommand(event);
            if (!result.equals(" ")) {
                System.out.println(result);
            }
        }

        if (this.bot.getFlagTrayPrivateMessage()) {
            String author = event.getAuthor().getName();

            if (this.bot.getFlagTrayPrivateMessage()) {
                String text = author + ": \"" + message + "\"";
                try {
                    utils.Functions.createSystemTray(text, "Discord");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (this.bot.getFlagLogging()) {
            LogMessage message = new LogMessage(event.getAuthor().getName());

            if (event.isFromGuild())
                message.addMessagePart("Guild", event.getGuild().getName());
            message.addMessagePart("TextChannel", event.getChannel().getName());
            message.addMessagePart("MessageContent", event.getMessage().getContentRaw());
            message.addMessagePart("AuthorId", event.getAuthor().getId());

            System.out.println(message.ToString());
        }
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        try {
            String result = this.commandManager.onReactionAdd(event);
            if (!result.equals(" ")) {
                System.out.println(result);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void onGuildMessageSpecialized(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();

        String idUser1 = "249565485052592129";
        String name1 = "даня";
        String word1 = "лох";

        if (message.toLowerCase().contains(name1)) {
            if (event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("<@" + idUser1 + "> " + word1).queue();
            } else {
                User user = event.getChannel().getJDA().getUserById(idUser1);
                assert user != null;
                user.openPrivateChannel().queue(channel -> channel.sendMessage("Ты " + word1 + "!").queue());
            }
        }

        if (message.toLowerCase().contains(word1) &&
                event.getGuild().getSelfMember().hasPermission(event.getChannel(),
                        Permission.MESSAGE_ADD_REACTION)) {
            MessageHistory messageHistory = new MessageHistory(event.getChannel());
            if (messageHistory.retrievePast(2).complete().get(1)
                    .getContentDisplay().toLowerCase().contains(name1))
                event.getMessage().addReaction(EmojiParser.parseToUnicode(":thumbsup|type_6:")).queue();
            event.getMessage().addReaction(EmojiParser.parseToUnicode(":rainbow_flag:")).queue();
        }

        String idUser2 = "654326469966823431";
        String name2 = "юля";
        String word2 = "лапочка ❤";

        if (message.toLowerCase().contains(name2)) {
            if (event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("<@" + idUser2 + "> " + word2).queue();
            } else {
                User user = event.getChannel().getJDA().getUserById(idUser2);
                assert user != null;
                user.openPrivateChannel().queue((channel) -> channel.sendMessage("Ты " + word2 + "!").queue());
            }
        }

        if (event.getAuthor().getId().equals(idUser2) &&
                event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_ADD_REACTION)) {
            MessageHistory messageHistory = new MessageHistory(event.getChannel());
            messageHistory.retrievePast(1).complete().get(0)
                    .addReaction(EmojiParser.parseToUnicode(":heart:")).queue();
        }

//        if (message.toLowerCase().contains("копейцев") &&
//                event.getGuild().getSelfMember().hasPermission(event.getChannel(),
//                        Permission.MESSAGE_ADD_REACTION)) {
//            event.getMessage().addReaction(EmojiParser.parseToUnicode(":rage:")).queue();
//        }

        if (message.toLowerCase().contains("дед") &&
                event.getGuild().getSelfMember().hasPermission(event.getChannel(),
                        Permission.MESSAGE_ADD_REACTION)) {
            event.getMessage().addReaction("\uD83C\uDDEB").queue();
        }
    }
}
