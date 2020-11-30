package discord.commands.executors;

import discord.DsBot;
import discord.commands.executors.others.DsMemeCreator;
import discord.commands.executors.others.DsPredictor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DsCommandExecutorSecret {
    private final DsBot bot;
    private final DsPredictor predictor;
    private final DsMemeCreator memeCreator;

    public DsCommandExecutorSecret(DsBot bot) {
        this.bot = bot;
        this.predictor = new DsPredictor();
        this.memeCreator = new DsMemeCreator();
    }

    public boolean send(@Nonnull GuildMessageReceivedEvent event, String[] commandParsed) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)){
            return false;
        }

        if (!this.bot.getCreators().contains(event.getAuthor().getId())) {
            return false;
        }

        User user;
        try {
            user = event.getJDA().getUserById(Long.parseLong(commandParsed[1]));
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (String str: Arrays.stream(commandParsed).skip(2).collect(Collectors.toList())) {
            messageBuilder.append(str).append(" ");
        }
        String message = messageBuilder.toString();

        String pattern1 = "`prediction`";

        while (message.contains(pattern1)) {
            message = message.replaceFirst(pattern1, this.predictor.getPrediction());
        }

        String pattern2 = "`meme`";
        while (message.contains(pattern2)) {
            message = message.replaceFirst(pattern2, "");
            MessageEmbed messageMeme = this.memeCreator.getMeme();
            if (messageMeme != null) {
                assert user != null;
                user.openPrivateChannel().queue(channel -> channel.sendMessage(messageMeme).queue());
            }
        }

        assert user != null;
        String finalMessage = message;
        user.openPrivateChannel().queue(channel -> channel.sendMessage(finalMessage).queue());

        return true;
    }

    public boolean send(@Nonnull PrivateMessageReceivedEvent event, String[] commandParsed) {
        if (!this.bot.getCreators().contains(event.getAuthor().getId())) {
            return false;
        }

        User user;
        try {
            user = event.getJDA().getUserById(Long.parseLong(commandParsed[1]));
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (String str: Arrays.stream(commandParsed).skip(2).collect(Collectors.toList())) {
            messageBuilder.append(str).append(" ");
        }
        String message = messageBuilder.toString();

        String pattern1 = "`prediction`";

        while (message.contains(pattern1)) {
            message = message.replaceFirst(pattern1, this.predictor.getPrediction());
        }

        String pattern2 = "`meme`";
        while (message.contains(pattern2)) {
            message = message.replaceFirst(pattern2, "");
            MessageEmbed messageMeme = this.memeCreator.getMeme();
            if (messageMeme != null) {
                assert user != null;
                user.openPrivateChannel().queue(channel -> channel.sendMessage(messageMeme).queue());
            }
        }

        assert user != null;
        String finalMessage = message;
        user.openPrivateChannel().queue(channel -> channel.sendMessage(finalMessage).queue());

        return true;
    }
}
