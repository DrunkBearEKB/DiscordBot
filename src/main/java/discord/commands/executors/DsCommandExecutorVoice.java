package discord.commands.executors;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;

import javax.annotation.Nonnull;
import java.util.List;

public class DsCommandExecutorVoice {

    public DsCommandExecutorVoice() {
    }

    public boolean kick(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.VOICE_MOVE_OTHERS)){
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to kick anyone out... :(").queue();
            }
            return false;
        }

        boolean flag = true;
        List<Member> members = DsCommandUtils.getListMembersMentionedAndFromVoiceChannel(event);
        for (Member member: members) {
            try {
                event.getGuild().kickVoiceMember(member).queue();
            } catch (HierarchyException exception) {
                flag = false;
            }
        }

        return flag;
    }

    public boolean mute(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.VOICE_MUTE_OTHERS)){
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to mute anyone... :(").queue();
            }
            return false;
        }

        boolean flag = true;
        List<Member> members = DsCommandUtils.getListMembersMentionedAndFromVoiceChannel(event);
        for (Member member: members) {
            try {
                member.mute(true).queue();
            } catch (HierarchyException exception) {
                flag = false;
            }
        }

        return flag;
    }

    public boolean unmute(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.VOICE_MUTE_OTHERS)) {
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to unmute anyone... :(").queue();
            }
            return false;
        }

        boolean flag = true;
        List<Member> members = DsCommandUtils.getListMembersMentionedAndFromVoiceChannel(event);
        for (Member member: members) {
            try {
                member.mute(false).queue();
            } catch (HierarchyException exception) {
                flag = false;
            }
        }

        return flag;
    }

    public boolean move(@Nonnull GuildMessageReceivedEvent event, String[] commandParsed) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.VOICE_MOVE_OTHERS)){
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to move anyone... :(").queue();
            }
            return false;
        }

        VoiceChannel voiceChannel = null;
        if (commandParsed[1].equals("afk") && event.getGuild().getAfkChannel() != null) {
            voiceChannel = event.getGuild().getAfkChannel();
        } else {
            boolean flagFound = false;
            for (VoiceChannel vc: event.getGuild().getVoiceChannels()) {
                if (commandParsed[1].contains(vc.getName())) {
                    voiceChannel = vc;
                    flagFound = true;
                    break;
                }
            }
            if (!flagFound) {
                event.getChannel().sendMessage("I didn't find a suitable voice channel named: \""
                        + commandParsed[1] + "\"... :(").queue();
                return false;
            }
        }

        boolean flag = true;
        List<Member> members = DsCommandUtils.getListMembersMentionedAndFromVoiceChannel(event);
        for (Member member: members) {
            try {
                member.getGuild().moveVoiceMember(member, voiceChannel).queue();
            } catch (Exception exception) {
                flag = false;
            }
        }

        return flag;
    }

    public boolean afk(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.VOICE_MOVE_OTHERS)){
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("I don't have permissions to move anyone... :(").queue();
            }
            return false;
        }

        if (event.getGuild().getAfkChannel() == null) {
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
                event.getChannel().sendMessage("This guild doesn't have `afk` voice channel... :(").queue();
            }
            return false;
        }

        List<Member> members = DsCommandUtils.getListMembersMentionedAndFromVoiceChannel(event);
        boolean flag = true;
        for (Member member: members) {
            try {
                member.getGuild().moveVoiceMember(member, event.getGuild().getAfkChannel()).queue();
            } catch (Exception exception) {
                flag = false;
            }
        }

        return flag;
    }
}
