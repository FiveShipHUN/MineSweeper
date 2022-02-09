package minesweeper.gfx;

import minesweeper.MineSweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonActionHandler implements MouseListener {

    private int x, y;
    private GameWindow game;
    private JButton btn;
    boolean isMine;
    private int state = 0;
    private int minesInNeighbor;
    private boolean revealed = false;

    public ButtonActionHandler(int x, int y, GameWindow game, JButton btn, boolean isMine) {
        this.x = x;
        this.y = y;
        this.game = game;
        this.btn = btn;
        this.isMine = isMine;
    }

    public void left() {
        if (state == 0 && game.frame() != null && game.frame().isActive()) {

            if (isMine) {
                game.forEachButton((btn, x, y) -> {
                    btn.setEnabled(false);
                    if (game.handler(x, y).isMine) {
                        btn.setIcon(MineSweeper.icons[3]);
                    }
                });
                JOptionPane.showMessageDialog(null, MineSweeper.string("LostL"), MineSweeper.string("Lost"), JOptionPane.WARNING_MESSAGE);
                MineSweeper.start();
            } else if (!revealed) {
                // btn.setText(isMine ? "Mine" : "Safe");
                revealed = true;

                minesInNeighbor = 0;
                var set = game.neighbors(x, y);
                for (var p : set) {
                    if (game.handler(p.x, p.y).isMine) {
                        minesInNeighbor++;
                    }
                }
                btn.setEnabled(false);
                if (minesInNeighbor == 0) {
                    for (var p : set) {
                        game.handler(p.x, p.y).left();
                    }
                    btn.setEnabled(false);
                } else {
                    btn.setText(minesInNeighbor + "");
                    btn.setFont(btn.getFont().deriveFont(Font.BOLD));
                }
                game.revealed++;
                if (game.revealed + game.numberOfMines() == game.size() * game.size()) {
                    JOptionPane.showMessageDialog(null, MineSweeper.string("Win"));
                    MineSweeper.start();
                }
            }
        }
    }

    public void right() {
        if (!revealed) {
            if (state == 1) {
                game.flagsPlaced--;
            }
            state++;
            if (state == 1) {
                game.flagsPlaced++;
            }
            state = state % 3;
            btn.setText("");
            game.mineFlagsPlaced.setText(MineSweeper.stringOr("Mines", "Mines: {0} / {1}", game.flagsPlaced, game.numberOfMines()));
            btn.setIcon(MineSweeper.icons[state]);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            left();
        } else if (SwingUtilities.isRightMouseButton(e)) {
            right();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
