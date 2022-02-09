package minesweeper.gfx;

import minesweeper.ButtonFunction;
import minesweeper.MineSweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class GameWindow {
    private JPanel panel1;
    private JPanel gameBtns;
    private JPanel controlBtnsPanel;
    private JButton restartButton;
    private JButton settingsBtn;
    public JLabel mineFlagsPlaced;

    private JButton[][] btns;
    //private JPanel[][] panels;

    private Random random = new Random();

    private JFrame frame;

    private int size;

    //private Set<JButton> mines;
    private int noMines;
    private ButtonActionHandler[][] handlers;

    public int revealed = 0;

    public int flagsPlaced;

    public JFrame frame() {
        return frame;
    }

    public void createButton(final int x, final int y) {
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(1, 1));
        JButton button = new MyButton();
        container.add(button);
        gameBtns.add(container);
        btns[x][y] = button;
        //panels[x][y] = container;
        // button.setText(String.format("%d, %d", x, y));
        button.addActionListener((ActionEvent e) -> generateMines(x, y));
        //button.setText("");
    }

    public Set<Point> neighbors(int x, int y) {
        HashSet<Point> set = new HashSet<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {

                int nX = x + dx;
                int nY = y + dy;
                if (0 <= nX && nX < size && 0 <= nY && nY < size) {
                    if (!(dx == 0 && dy == 0)) {
                        set.add(new Point(nX, nY));
                    }
                }

            }
        }
        return set;
    }

    public JButton button(int x, int y) {

        return btns[x][y];
    }

    public int size() {
        return size;
    }

    public JButton buttonSafe(int x, int y) {
        if (x < 0) {
            x = 0;
        }
        if (x >= size) {
            x = size - 1;
        }
        if (y < 0) {
            y = 0;
        }
        if (y >= size) {
            y = size - 1;
        }
        return button(x, y);
    }

    public void forEachButton(ButtonFunction f) {
        for (int x = 0; x < size(); x++) {
            for (int y = 0; y < size(); y++) {
                f.run(button(x, y), x, y);
            }
        }
    }

    public void generateMines(int startX, int startY) {
        int mines = numberOfMines();
        java.util.List<Point> points = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                points.add(new Point(x, y));
            }
        }

        var shuffleCount = random.nextInt(128);

        for (var i = 0; i < shuffleCount; i++) {
            Collections.shuffle(points);
        }

        for (var i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            var btn = button(p.x, p.y);
            btn.removeActionListener(btn.getActionListeners()[0]);
            if (Math.abs(p.x - startX) <= 1 && Math.abs(p.y - startY) <= 1 && i < mines) {
                mines++;
            }
            var h = new ButtonActionHandler(p.x, p.y, this, btn, !(Math.abs(p.x - startX) <= 1 && Math.abs(p.y - startY) <= 1) && i < mines);
            btn.addMouseListener(h);
            //panels[p.x][p.y].addMouseListener(h);
            handlers[p.x][p.y] = h;
            if (h.isMine) {
                // btn.setText("M");
            }
        }
        try {
            handlers[startX][startY].left();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("X: " + startX + ", Y: " + startY + ", Size: " + size + ", Area: " + points.size());
        }
    }


    public int numberOfMines() {
        return noMines;
    }

    public ButtonActionHandler handler(int x, int y) {
        return handlers[x][y];
    }

    public static GameWindow create(int size, int mines, long seed) {
        var frame = new JFrame(MineSweeper.stringOr("Title", "MineSweeper"));

        var w = new GameWindow();
        //frame.setUndecorated(true);
        w.frame = frame;
        w.random = new Random(seed);
        w.btns = new JButton[size][size];
        // w.panels = new JPanel[size][size];
        w.handlers = new ButtonActionHandler[size][size];
        w.flagsPlaced = 0;
        w.noMines = mines;
        w.size = size;
        w.settingsBtn.addActionListener((e) -> {
            SettingsDialog.settings();
        });

        var gridLayout = new SquareLayoutGrid(size, size);
        w.gameBtns.setLayout(gridLayout);
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                w.createButton(x, y);
            }
        }

        for (int x = 0; x < w.size(); x++) {
            for (int y = 0; y < w.size(); y++) {
                w.button(x, y).setFocusable(false);

            }
        }

        w.restartButton.addActionListener((e) -> {
            frame.dispose();
            MineSweeper.start();
        });


        frame.setContentPane(w.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // frame.setBounds(0, 0, 800, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setIconImage(MineSweeper.icon);

        // Init localized text
        w.restartButton.setText(MineSweeper.string("Restart"));
        w.settingsBtn.setText(MineSweeper.string("Settings"));
        w.mineFlagsPlaced.setText(MineSweeper.stringOr("Mines", "Mines: {0} / {1}", w.flagsPlaced, w.numberOfMines()));


        return w;
    }

}
