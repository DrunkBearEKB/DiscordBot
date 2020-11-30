package discord.commands.executors;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DsCommandUtils {
    public static List<Member> getListMembersMentioned(@Nonnull GuildMessageReceivedEvent event) {
        List<Member> result = new ArrayList<>();

        for (Member member: event.getGuild().getMembers()) {
            if (event.getMessage().getMentionedMembers().contains(member)) {
                result.add(member);
            } else {
                for (Role role: member.getRoles()) {
                    if (event.getMessage().getMentionedRoles().contains(role) && !result.contains(member)) {
                        result.add(member);
                        break;
                    }
                }
            }
        }

        return result;
    }

    public static List<Member> getListMembersMentionedAndFromVoiceChannel(@Nonnull GuildMessageReceivedEvent event) {
        if (!event.getMember().getVoiceState().inVoiceChannel()) {
            return new ArrayList<>();
        }

        List<Member> result = new ArrayList<>();
        List<Member> members = event.getMember().getVoiceState().getChannel().getMembers().stream()
                .filter(member -> Objects.requireNonNull(member.getVoiceState()).inVoiceChannel())
                .collect(Collectors.toList());

        for (Member member: members) {
            if (event.getMessage().getMentionedMembers().contains(member)) {
                result.add(member);
            } else {
                for (Role role: member.getRoles()) {
                    if (event.getMessage().getMentionedRoles().contains(role) && !result.contains(member)) {
                        result.add(member);
                        break;
                    }
                }
            }
        }

        return result;
    }
}
