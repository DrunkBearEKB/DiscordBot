package discord.commands.executors.others;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class DsMemeCreator {
    public DsMemeCreator() {

    }

    public MessageEmbed getMeme() {
        JSONParser parser = new JSONParser();
        String postLink = "";
        String title = "";
        String url = "";

        try {
            URL urlMeme = new URL("https://meme-api.herokuapp.com/gimme");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlMeme.openConnection().getInputStream()));

            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                JSONArray array = new JSONArray();
                array.add(parser.parse(lines));

                for (Object obj: array) {
                    JSONObject jsonObject = (JSONObject)obj;

                    postLink = (String)jsonObject.get("postlink");
                    title = (String)jsonObject.get("title");
                    url = (String)jsonObject.get("url");
                }
            }

            bufferedReader.close();
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle(title, postLink)
                    .setImage(url)
                    .setColor(Color.ORANGE);

            return embedBuilder.build();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
