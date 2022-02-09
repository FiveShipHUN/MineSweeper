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
    private JLabel minesL;
    private JLabel sizeL;
    private JLabel seedL;
    private JLabel langL;
    private JComboBox<String> langCB;

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

        // Init locale
        sd.minesL.setText(MineSweeper.string("MinesL"));
        sd.seedL.setText(MineSweeper.string("SeedL"));
        sd.sizeL.setText(MineSweeper.string("SizeL"));
        sd.cancelButton.setText(MineSweeper.string("Cancel"));
        sd.langL.setText(MineSweeper.string("Lang"));
        sd.langCB.setModel(new DefaultComboBoxModel<>() {
            @Override
            public int getSize() {
                return 2;
            }

            @Override
            public String getElementAt(int index) {
                return switch (index) {
                    case 0 -> "English | ENG";
                    case 1 -> "Magyar | HUN";
                    default -> "";
                };
            }
        });
        sd.langCB.setSelectedIndex(MineSweeper.cfg.lang.equals("english") ? 0 : 1);
        sd.langCB.addActionListener((e) -> {
            MineSweeper.cfg.lang = sd.langCB.getSelectedIndex() == 0 ? "english" : "hungarian";
            MineSweeper.cfg.save();
            MineSweeper.loadLang();
        });
    }
}
