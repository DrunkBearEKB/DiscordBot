package discord.commands.executors;

import discord.DsBot;
import discord.commands.executors.others.DsPredictor;
import discord.commands.executors.others.DsMemeCreator;
import discord.commands.executors.others.DsTranslator;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class DsCommandExecutorOthers {
    private final DsBot bot;
    private final DsPredictor predictor;
    private final DsMemeCreator memeCreator;
    private final DsTranslator translator;

    public DsCommandExecutorOthers(DsBot bot) {
        this.bot = bot;
        this.predictor = new DsPredictor();
        this.memeCreator = new DsMemeCreator();
        this.translator = new DsTranslator();
    }

    public boolean clear(@Nonnull GuildMessageReceivedEvent event, String[] commandParsed) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_HISTORY)){
            if (event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to clear messages... :(").queue();
            }
            return false;
        }

        try {
            int amount = Integer.parseInt(commandParsed[1]) + 1;

            while (true) {
                try {
                    MessageHistory messageHistory = new MessageHistory(event.getChannel());
                    List<Message> messages = messageHistory.retrievePast(amount).complete();
                    for (Message message: messages) {
                        message.delete().queue();
                    }
                    break;
                } catch (IllegalArgumentException exception) {
                    amount--;
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean translate(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)){
            return false;
        }

        String contentTranslated = translator.translate(event.getMessage().getContentDisplay());

        if (contentTranslated != null) {
            event.getChannel().sendMessage(contentTranslated).queue();
            return true;
        }

        return false;
    }

    public boolean translate(@Nonnull PrivateMessageReceivedEvent event) {
        String contentTranslated = translator.translate(event.getMessage().getContentDisplay());

        if (contentTranslated != null) {
            event.getChannel().sendMessage(contentTranslated).queue();
            return true;
        }

        return false;
    }

    public boolean translate(@Nonnull MessageReactionAddEvent event) {
        if (event.isFromGuild() &&
                !event.getGuild().getSelfMember().hasPermission(event.getTextChannel(), Permission.MESSAGE_WRITE)){
            return false;
        }

        String language = "";
        if (event.getReactionEmote().getEmoji().equals("\uD83C\uDDF7\uD83C\uDDFA")) {
            language = "ru";
        } else if (event.getReactionEmote().getEmoji().equals("\uD83C\uDDEC\uD83C\uDDE7")) {
            language = "en";
        }

        String contentTranslated = translator.translate("`" + language + "` " + event.getChannel()
                .retrieveMessageById(event.getMessageId()).complete().getContentRaw());

        if (contentTranslated != null) {
            event.getChannel().sendMessage(contentTranslated).queue();
            return true;
        }

        return false;
    }

    public boolean prediction(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)){
            return false;
        }

        try {
            event.getChannel().sendMessage(this.predictor.getPrediction()).queue();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean prediction(@Nonnull PrivateMessageReceivedEvent event) {
        try {
            event.getChannel().sendMessage(this.predictor.getPrediction()).queue();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean meme(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)){
            return false;
        }

        try {
            event.getChannel().sendMessage(this.memeCreator.getMeme()).queue();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean meme(@Nonnull PrivateMessageReceivedEvent event) {
        try {
            event.getChannel().sendMessage(this.memeCreator.getMeme()).queue();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean help(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)){
            return false;
        }

        List<String> messagesText = new ArrayList<>();
        messagesText.add(
            "__**Административные команды**__:\n" +
            "      **ban** _[упоминания участиников и ролей]_ **-** выгоняет участников из канала\n" +
            "      **giveRoles** _[упоминания участиников и ролей]_ **-** добавялет упомянутым участникам упомнятые роли\n" +
            "      **removeRoles** _[упоминания участиников и ролей]_ **-** забирает у упомнятутых участников упомнятые роли\n" +
            "      **addVoiceChannel** _[имя голосового канала]_ **-** создаёт голосовой канал по имени\n" +
            "      **moveVoiceChannel** _[имя голосового канала]_ _[смещение]_ **-** перемещает голосовой канал по его имени на смещение вниз\n" +
            "      **removeVoiceChannel** _[имя голосового канала]_ **-** удаляет голосовой канал по его имени\n" +
            "      **addTextChannel** _[имя текстового канала]_ **-** создаёт текстовый канал по имени\n" +
            "      **moveTextChannel** _[имя текстового канала]_ _[смещение]_ **-** перемещает текстовый канал по его имени на смещение вниз\n" +
            "      **removeTextChannel** _[имя текстового канала]_ **-** удаляет текстовый канал по его имени");
        messagesText.add(
            "__**Команды для управления голосовыми каналами:**__\n" +
            "      **kick** _[упоминания участиников и ролей]_ **-** выгоняет участников из голосовых каналов по их упоминаниям или по их ролям\n" +
            "      **mute** _[упоминания участиников и ролей]_ **-** мутит участников в голосовых каналах по их упоминаниям или по их ролям\n" +
            "      **unmute** _[упоминания участиников и ролей]_ **-** размучивает участников в голосовых каналах по их упоминаниям или по их ролям\n" +
            "      **move** _[голосовой канал]_ _[упоминания участиников и ролей]_ **-** перемещает участников по их упоминаниям или по их ролям в канал по его имени\n" +
            "      **afk** _[упоминания участиников и ролей]_ **-** перемещает участников по их упоминаниям или по их ролям в 'afk' канал"
        );
        messagesText.add(
            "__**Команды для управления ботом в голосовом канале:**__\n" +
            "      **join** **-** бот заходит в голосовой канал, в котором находится человек, написавший данное сообщение\n" +
            "      **transport** _[голосовой канал]_ **-** бот переходит в голосовой канал по его имени\n" +
            "      **leave** **-** бот выходит из голосового канала, в котором находится"
        );
        messagesText.add(
            "__**Общие:**__\n" +
            "      **clear** _[кол-во сообещний]_ **-** удаляет последние сообщения в данном текстовом канале по их кол-ву\n" +
            "      **translate** _{language}_ _[текст]_ **-** переводит текст на заданный язык (язык указывается в данных символах - `)\n" +
            "      **prediction** **-** отправляет в текущий текстовый канал предсказание для Вас\n" +
            "      **meme** **-** отправляет мем в текующий текстовый канал\n" +
            "      **help** **-** показывает данное сообщение"
        );

        if (event.getMessage().getContentDisplay().split(" ").length > 1 &&
                event.getMessage().getContentDisplay().split(" ")[1].equals("-roots") &&
                this.bot.getCreators().contains(event.getAuthor().getId())) {
            messagesText.add(
                "__**Скрытые команды:**__\n" +
                "      **send** _[id пользователя]_ _[сообщение]_ **-** отправляет сообщение конкретнному пользователю от имени бота; (использование `prediction` в сообщении заменится на предсказание; использование `meme` в сообщении заменится на мем)"
            );
        }

        try {
            for (String messageText: messagesText) {
                event.getChannel().sendMessage(messageText).queue();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean help(@Nonnull PrivateMessageReceivedEvent event) {
        List<String> messagesText = new ArrayList<>();

        messagesText.add(
            "__**Общие:**__\n" +
            "      **translate** _{language}_ _[текст]_ **-** переводит текст на заданный язык (язык указывается в данных символах - `)\n" +
            "      **prediction** **-** отправляет в текущий текстовый канал предсказание для Вас\n" +
            "      **meme** **-** отправляет мем в текующий текстовый канал\n" +
            "      **help** **-** показывает данное сообщение"
        );

        if (this.bot.getCreators().contains(event.getAuthor().getId())) {
            messagesText.add(
                "__**Скрытые команды:**__\n" +
                "      **send** _[id пользователя]_ _[сообщение]_ **-** отправляет сообщение конкретнному пользователю от имени бота; (использование `prediction` в сообщении заменится на предсказание; использование `meme` в сообщении заменится на мем)"
            );
        }

        try {
            for (String messageText: messagesText) {
                event.getChannel().sendMessage(messageText).queue();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }
}
