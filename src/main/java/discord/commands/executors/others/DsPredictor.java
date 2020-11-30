package discord.commands.executors.others;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

public class DsPredictor {
    private final List<String> predictions;

    public DsPredictor() {
        this.predictions = new ArrayList<>();

        try {
            String dir = System.getProperty("user.dir");
            File file = new File(dir + "/src/main/resources/predictions/predictions.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                this.predictions.add(line);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public String getPrediction() {
        return this.predictions.get((int)(Math.random() * (double)(this.predictions.size() + 1)));
    }
}
