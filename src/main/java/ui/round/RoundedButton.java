package ui.round;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    public RoundedButton(String label) {
        super(label);

        // Make the button completely transparent.
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setRolloverEnabled(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            // If button is pressed, use a different color
            g.setColor(Color.lightGray);
        } else if (getModel().isRollover()) {
            g.setColor(getBackground().darker());
        } else {
            // Regular color
            g.setColor(getBackground());
        }

        // Draw shadow
        Color c = g.getColor();
        g.setColor(g.getColor().darker());
        g.fillRoundRect(1, 1, getWidth() - 1, getHeight() - 1, 12, 12);
        g.setColor(c);

        // Draw a rounded rectangle in the background of the button
        g.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 12, 12);
        super.paintComponent(g);
    }

}
