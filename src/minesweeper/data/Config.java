package minesweeper.data;

import minesweeper.MineSweeper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Config {

    public String lang = "english";

    public static Config loadOrNew() {
        try {
            return MineSweeper.JSON.readValue(new File("cfg.json"), Config.class);
        } catch (Exception e) {
            if (!(e instanceof FileNotFoundException)) {
                System.err.println("Could not load config file.");
                e.printStackTrace();
            }
            return new Config();
        }
    }

    public void save() {
        try {
            MineSweeper.JSON.writerWithDefaultPrettyPrinter().writeValue(new File("cfg.json"), this);
        } catch (IOException e) {
            System.err.println("Could not save config.");
            e.printStackTrace();
        }
    }

}
