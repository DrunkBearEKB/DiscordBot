package discord.commands.executors.others;

import com.darkprograms.speech.translator.GoogleTranslate;

import java.io.IOException;

public class DsTranslator {
    public DsTranslator() {

    }

    public String translate(String content) {
        String language = "ru";
        if (content.contains("`") && content.indexOf("`") != content.lastIndexOf("`")) {
            language = content.substring(content.indexOf("`") + 1);
            language = language.substring(0, language.indexOf("`"));
        }

        String text = content.substring(content.indexOf(" ") + 1);
        text = text.replace("`" + language + "`", "");

        try {
            return GoogleTranslate.translate(language, text);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
