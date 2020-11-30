package discord.commands.executors;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.requests.restaction.order.ChannelOrderAction;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class DsCommandExecutorAdministrative {

    public DsCommandExecutorAdministrative() {

    }

    public boolean ban(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)){
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to ban anyone out... :(").queue();
            }
            return false;
        }

        int amountDays;
        try {
            amountDays = Integer.parseInt(Arrays.asList(event.getMessage().getContentRaw().split(" ")).get(1));
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        List<Member> members = DsCommandUtils.getListMembersMentioned(event);
        for (Member member: members) {
            try {
                event.getGuild().ban(member, amountDays);
            } catch (HierarchyException exception) {
                return false;
            }
        }

        for (Role role: event.getMessage().getMentionedRoles()) {
            for (Member member: event.getGuild().getMembersWithRoles(role)) {
                try {
                    event.getGuild().ban(member, amountDays);
                } catch (HierarchyException exception) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean giveRoles(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES)){
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to manage roles... :(").queue();
            }
            return false;
        }

        List<Member> members = DsCommandUtils.getListMembersMentioned(event);
        List<Role> roles = event.getMessage().getMentionedRoles();

        for (Member member: members) {
            for (Role role: roles) {
                try {
                    event.getGuild().addRoleToMember(member, role).queue();
                } catch (HierarchyException exception) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean removeRoles(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES)){
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to manage roles... :(").queue();
            }
            return false;
        }

        List<Member> members = DsCommandUtils.getListMembersMentioned(event);
        List<Role> roles = event.getMessage().getMentionedRoles();

        for (Member member: members) {
            for (Role role: roles) {
                if (member.getRoles().contains(role)) {
                    try {
                        event.getGuild().removeRoleFromMember(member, role).queue();
                    } catch (HierarchyException exception) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean addVoiceChannel(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_CHANNEL)){
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to manage channels... :(").queue();
            }
            return false;
        }

        String voiceChannelName = event.getMessage().getContentRaw()
                .substring(event.getMessage().getContentRaw().indexOf(' '));
        while (voiceChannelName.contains("  ")) {
            voiceChannelName = voiceChannelName.replace("  ", " ");
        }

        while (voiceChannelName.lastIndexOf(" ") == voiceChannelName.length() - 1) {
            voiceChannelName = voiceChannelName.substring(0, voiceChannelName.length() - 1);
        }

        try {
            event.getGuild().createVoiceChannel(voiceChannelName).queue();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean moveVoiceChannel(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_CHANNEL)){
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to manage channels... :(").queue();
            }
            return false;
        }

        String content = event.getMessage().getContentRaw();
        while (content.contains("  ")) {
            content = content.replace("  ", " ");
        }
        String[] contentParsed = content.split(" ");

        String voiceChannelName = contentParsed[1];
        int num = Integer.parseInt(contentParsed[2]);

        while (voiceChannelName.lastIndexOf(" ") == voiceChannelName.length() - 1) {
            voiceChannelName = voiceChannelName.substring(0, voiceChannelName.length() - 1);
        }

        try {
            ChannelOrderAction channelOrderAction = event.getGuild().modifyVoiceChannelPositions()
                    .selectPosition(event.getGuild()
                            .getVoiceChannelsByName(voiceChannelName, true).get(0));
            if (num >= 0) {
                while (true) {
                    try {
                        channelOrderAction.moveDown(num).queue();
                        break;
                    } catch (Exception exception) {
                        num -= 1;
                    }
                }
            } else {
                num *= -1;
                while (true) {
                    try {
                        channelOrderAction.moveUp(num).queue();
                        break;
                    } catch (Exception exception) {
                        num -= 1;
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean removeVoiceChannel(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_CHANNEL)){
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to manage channels... :(").queue();
            }
            return false;
        }

        String voiceChannelName = event.getMessage().getContentRaw()
                .substring(event.getMessage().getContentRaw().indexOf(' ') + 1);
        while (voiceChannelName.lastIndexOf(" ") == voiceChannelName.length() - 1) {
            voiceChannelName = voiceChannelName.substring(0, voiceChannelName.length() - 1);
        }

        try {
            for (VoiceChannel voiceChannel: event.getGuild().getVoiceChannels()) {
                if (voiceChannel.getName().contains(voiceChannelName)) {
                    voiceChannel.delete().queue();
                    return true;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public boolean addTextChannel(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_CHANNEL)){
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to manage channels... :(").queue();
            }
            return false;
        }

        try {
            String textChannelName = event.getMessage().getContentRaw()
                    .substring(event.getMessage().getContentRaw().indexOf(' '));
            event.getGuild().createTextChannel(textChannelName).queue();
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean moveTextChannel(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_CHANNEL)){
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to manage channels... :(").queue();
            }
            return false;
        }

        String content = event.getMessage().getContentRaw();
        while (content.contains("  ")) {
            content = content.replace("  ", " ");
        }
        String[] contentParsed = content.split(" ");

        String textChannelName = contentParsed[1];
        int num = Integer.parseInt(contentParsed[2]);

        while (textChannelName.lastIndexOf(" ") == textChannelName.length() - 1) {
            textChannelName = textChannelName.substring(0, textChannelName.length() - 1);
        }

        try {
            ChannelOrderAction channelOrderAction = event.getGuild().modifyTextChannelPositions()
                    .selectPosition(event.getGuild()
                            .getTextChannelsByName(textChannelName, true).get(0));
            if (num >= 0) {
                while (true) {
                    try {
                        channelOrderAction.moveDown(num).queue();
                        break;
                    } catch (Exception exception) {
                        num -= 1;
                    }
                }
            } else {
                num *= -1;
                while (true) {
                    try {
                        channelOrderAction.moveUp(num ).queue();
                        break;
                    } catch (Exception exception) {
                        num -= 1;
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean removeTextChannel(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_CHANNEL)){
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to manage channels... :(").queue();
            }
            return false;
        }

        try {
            String textChannelName = event.getMessage().getContentDisplay()
                    .substring(event.getMessage().getContentRaw().indexOf(' ') + 1);
            for (TextChannel textChannel: event.getGuild().getTextChannels()) {
                if (textChannel.getName().contains(textChannelName.substring(0, textChannelName.length() - 1))) {
                    textChannel.delete().queue();
                    return true;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }
}
