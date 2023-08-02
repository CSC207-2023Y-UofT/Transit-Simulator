package ui;

import javax.swing.*;
import java.awt.*;

public class RoundedLabel extends JLabel {
    public RoundedLabel(String text) {
        super(text);

        setPreferredSize(new Dimension(50, 20));

        setOpaque(false);
    }


    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
}
