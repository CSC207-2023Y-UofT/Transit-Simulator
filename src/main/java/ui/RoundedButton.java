package ui;

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
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            // If button is pressed, use a different color
            g.setColor(Color.lightGray);
        } else {
            // Regular color
            g.setColor(getBackground());
        }

        // Draw a rounded rectangle in the background of the button
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Draw the border of the button
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
    }

}
