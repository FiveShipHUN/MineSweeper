package minesweeper.gfx;

import minesweeper.MineSweeper;

import javax.swing.*;

public class SettingsDialog {
    private JPanel panel;
    private JTextField mines;
    private JTextField size;
    private JTextField seed;
    private JButton cancelButton;
    private JButton okButton;

    public static void settings() {
        var sd = new SettingsDialog();
        JFrame frame = new JFrame(MineSweeper.string("Settings"));
        frame.setIconImage(MineSweeper.icon);
        frame.setContentPane(sd.panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        sd.mines.setText(MineSweeper.mines + "");
        sd.seed.setText(MineSweeper.seed + "");
        sd.size.setText(MineSweeper.size + "");
        sd.okButton.addActionListener((e) -> {
            frame.dispose();
            MineSweeper.seed = Long.parseLong(sd.seed.getText());
            MineSweeper.mines = Integer.parseInt(sd.mines.getText());
            MineSweeper.size = Integer.parseInt(sd.size.getText());
            MineSweeper.start();
        });
        sd.cancelButton.addActionListener((e) -> frame.dispose());
    }
}
