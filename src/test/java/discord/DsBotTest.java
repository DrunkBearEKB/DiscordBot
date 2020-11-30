package discord;

import org.junit.Assert;
import org.junit.Test;

import java.util.IdentityHashMap;
import java.util.Map;


public class DsBotTest {
    private final String token = "NzM5NTg2MDQ4MDM1NzgyNjk2.XycnLg.rlGeLabwOd8D7qlwGIFe75RzdHk";

    @Test
    public void testBotDefault() throws Exception {
        Map<String, Boolean> flags = new IdentityHashMap<>();
        DsBot bot = new DsBot(this.token, flags);

        Assert.assertEquals('!', bot.getSymbolCommand());
        Assert.assertEquals("405767026422972416 654326469966823431", bot.getCreators());
    }

    @Test
    public void testBotFlagsDefault() throws Exception {
        Map<String, Boolean> flags = new IdentityHashMap<>();
        DsBot bot = new DsBot(this.token, flags);

        Assert.assertFalse(bot.getFlagLogging());
        Assert.assertFalse(bot.getFlagTrayCreators());
        Assert.assertFalse(bot.getFlagTrayPrivateMessage());
        Assert.assertFalse(bot.getFlagTrayGuildMessage());
        Assert.assertFalse(bot.getFlagTrayGuildVoiceChannelJoin());
        Assert.assertFalse(bot.getFlagTrayGuildVoiceChannelMove());
        Assert.assertFalse(bot.getFlagTrayGuildVoiceChannelLeave());
    }

    @Test
    public void testBotFlagsCase1() throws Exception {
        Map<String, Boolean> flags = new IdentityHashMap<>();
        flags.put("flagLogging", true);
        flags.put("flagTrayCreators", false);
        flags.put("flagTrayPrivateMessage", false);
        flags.put("flagTrayGuildMessage", false);
        flags.put("flagTrayGuildVoiceChannelJoin", true);
        flags.put("flagTrayGuildVoiceChannelMove", false);
        flags.put("flagTrayGuildVoiceChannelLeave", false);
        DsBot bot = new DsBot(this.token, flags);

        Assert.assertTrue(bot.getFlagLogging());
        Assert.assertFalse(bot.getFlagTrayCreators());
        Assert.assertFalse(bot.getFlagTrayPrivateMessage());
        Assert.assertFalse(bot.getFlagTrayGuildMessage());
        Assert.assertTrue(bot.getFlagTrayGuildVoiceChannelJoin());
        Assert.assertFalse(bot.getFlagTrayGuildVoiceChannelMove());
        Assert.assertFalse(bot.getFlagTrayGuildVoiceChannelLeave());
    }

    @Test
    public void testBotFlagsCase2() throws Exception {
        Map<String, Boolean> flags = new IdentityHashMap<>();
        flags.put("flagLogging", false);
        flags.put("flagTrayCreators", false);
        flags.put("flagTrayPrivateMessage", true);
        flags.put("flagTrayGuildMessage", false);
        flags.put("flagTrayGuildVoiceChannelJoin", true);
        flags.put("flagTrayGuildVoiceChannelMove", false);
        flags.put("flagTrayGuildVoiceChannelLeave", true);
        DsBot bot = new DsBot(this.token, flags);

        Assert.assertFalse(bot.getFlagLogging());
        Assert.assertFalse(bot.getFlagTrayCreators());
        Assert.assertTrue(bot.getFlagTrayPrivateMessage());
        Assert.assertFalse(bot.getFlagTrayGuildMessage());
        Assert.assertTrue(bot.getFlagTrayGuildVoiceChannelJoin());
        Assert.assertFalse(bot.getFlagTrayGuildVoiceChannelMove());
        Assert.assertTrue(bot.getFlagTrayGuildVoiceChannelLeave());
    }

    @Test
    public void testBotFlagsCase3() throws Exception {
        Map<String, Boolean> flags = new IdentityHashMap<>();
        flags.put("flagLogging", true);
        flags.put("flagTrayCreators", false);
        flags.put("flagTrayPrivateMessage", false);
        flags.put("flagTrayGuildMessage", true);
        flags.put("flagTrayGuildVoiceChannelJoin", false);
        flags.put("flagTrayGuildVoiceChannelMove", true);
        flags.put("flagTrayGuildVoiceChannelLeave", true);
        DsBot bot = new DsBot(this.token, flags);

        Assert.assertTrue(bot.getFlagLogging());
        Assert.assertFalse(bot.getFlagTrayCreators());
        Assert.assertFalse(bot.getFlagTrayPrivateMessage());
        Assert.assertTrue(bot.getFlagTrayGuildMessage());
        Assert.assertFalse(bot.getFlagTrayGuildVoiceChannelJoin());
        Assert.assertTrue(bot.getFlagTrayGuildVoiceChannelMove());
        Assert.assertTrue(bot.getFlagTrayGuildVoiceChannelLeave());
    }
}
