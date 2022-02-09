package minesweeper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatDarculaLaf;
import minesweeper.data.Config;
import minesweeper.gfx.GameWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class MineSweeper {

    private static GameWindow game;

    public static final Icon[] icons = new Icon[4];
    public static Config cfg;

    public static final ObjectMapper JSON = new ObjectMapper();
    public static HashMap<String, String> lang;

    public static BufferedImage icon;

    public static GameWindow game() {
        return game;
    }

    public static void initJSON() {
        JSON.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String string(String key, Object... args) {
        return stringOr(key, key, args);
    }

    public static String stringOr(String key, String or, Object... args) {
        return pushArgs(lang.getOrDefault(key, or), args);
    }

    public static String pushArgs(String s, Object... args) {
        for (int i = 0; i < args.length; i++) {
            s = s.replace("{" + i + "}", args[i].toString());
        }
        return s;
    }

    public static void main(String[] args) {
        initJSON();
        cfg = Config.loadOrNew();
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        try {
            icons[0] = null;
            icons[1] = new ImageIcon(ImageIO.read(Objects.requireNonNull(MineSweeper.class.getResourceAsStream("/minesweeper/assets/textures/mine_mark.png"))));
            icons[2] = new ImageIcon(ImageIO.read(Objects.requireNonNull(MineSweeper.class.getResourceAsStream("/minesweeper/assets/textures/mine_not_sure.png"))));
            icons[3] = new ImageIcon(icon = ImageIO.read(Objects.requireNonNull(MineSweeper.class.getResourceAsStream("/minesweeper/assets/textures/mine.png"))));
            // UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch (Exception e) {
            System.err.println("Can't load assets. Exiting...");
            e.printStackTrace();
            System.exit(0);
            return;
        }
        start();
    }

    public static int size = 10;
    public static int mines = 8;
    public static long seed = -1;

    public static void start() {
        start(size, mines);
    }

    public static void start(int size, int mines) {
        start(size, mines, seed);
    }

    public static void start(int size, int mines, long seed) {
        if (game != null && game.frame() != null) game.frame().dispose();
        game = GameWindow.create(size, mines, seed > 0 ? seed : new Random().nextInt());

    }
}
