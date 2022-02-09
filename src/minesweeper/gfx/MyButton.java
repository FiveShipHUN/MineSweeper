package minesweeper.gfx;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {

    public MyButton() {
    }

    public MyButton(Icon icon) {
        super(icon);
    }

    public MyButton(String text) {
        super(text);
    }

    public MyButton(Action a) {
        super(a);
    }

    public MyButton(String text, Icon icon) {
        super(text, icon);
    }

    @Override
    public Icon getIcon() {
        Icon i = super.getIcon();
        if (i instanceof ImageIcon ii) {
            var img = ii.getImage();
            img = img.getScaledInstance(getWidth() - 5, getHeight() - 5, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return i;
    }
}
