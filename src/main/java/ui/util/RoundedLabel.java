package ui.util;

import javax.swing.*;
import java.awt.*;

/**
 * RoundedLabel is a JLabel that is rounded.
 */
public class RoundedLabel extends JLabel {
    public RoundedLabel(String text) {
        super(text);
        setPreferredSize(new Dimension(50, 20));
        setOpaque(false);
    }

    /**
     * Paints the label.
     *
     * @param g the Graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        super.paintComponent(g);
    }

    /**
     * Paints the border of the label.
     *
     * @param g the Graphics object
     */
    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
    }

}
