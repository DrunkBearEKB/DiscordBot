import discord.DsBot;
import utils.Functions;

import org.apache.log4j.BasicConfigurator;
import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();

        String token = getTokenFromConfig();
        Map<String, Boolean> flags = getFlagsFromConfig();

        boolean flagNotStarted = true;
        while (flagNotStarted) {
            try {
                int timeReconnectionSeconds = 10;
                while (!Functions.checkNetAvailable()) {
                    utils.Functions.createSystemTray
                            ("DiscordBot can't be started! Internet connection is shutdown!",
                                    "error");
                    System.out.println("DiscordBot will try to start after " +
                            timeReconnectionSeconds + " seconds...");
                    Thread.sleep(timeReconnectionSeconds * 1000);

                }
                DsBot bot = new DsBot(token, flags);
                flagNotStarted = false;
            } catch (LoginException exception) {
                utils.Functions.createSystemTray
                        ("DiscordBot can't be started! Please input a new token for discord bot!",
                                "error");
                System.out.print("Please input a new token for discord bot: ");

                Scanner scanner = new Scanner(System.in);
                token = scanner.nextLine();
            }
        }
    }

    private static String getTokenFromConfig() throws IOException {
        Properties props = new Properties();
        String dir = System.getProperty("user.dir");
        props.load(new FileInputStream(new File(dir + "/src/main/resources/config.ini")));

        return props.getProperty("token1") + props.getProperty("token2");
    }

    private static Map<String, Boolean> getFlagsFromConfig() throws IOException {
        Properties props = new Properties();
        String dir = System.getProperty("user.dir");
        props.load(new FileInputStream(new File(dir + "/src/main/resources/config.ini")));

        Map<String, Boolean> result = new IdentityHashMap<>();
        result.put("flagLogging",
                props.getProperty("flagLogging").equals("true"));
        result.put("flagTrayCreators",
                props.getProperty("flagTrayCreators").equals("true"));
        result.put("flagTrayPrivateMessage",
                props.getProperty("flagTrayPrivateMessage").equals("true"));
        result.put("flagTrayGuildMessage",
                props.getProperty("flagTrayGuildMessage").equals("true"));
        result.put("flagTrayGuildVoiceChannelJoin",
                props.getProperty("flagTrayGuildVoiceChannelJoin").equals("true"));
        result.put("flagTrayGuildVoiceChannelMove",
                props.getProperty("flagTrayGuildVoiceChannelMove").equals("true"));
        result.put("flagTrayGuildVoiceChannelLeave",
                props.getProperty("flagTrayGuildVoiceChannelLeave").equals("true"));

        return result;
    }
}
