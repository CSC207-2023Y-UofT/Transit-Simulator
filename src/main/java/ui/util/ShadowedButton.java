package ui.util;

import javax.swing.*;
import java.awt.*;

/**
 * RoundedButton is a JButton that is rounded.
 */
public class ShadowedButton extends JButton {

    private boolean rounded = true;

    /**
     * Constructs a new RoundedButton with the given label.
     *
     * @param label the label of the button
     */
    public ShadowedButton(String label) {
        super(label);

        // Make the button completely transparent.
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setRolloverEnabled(true);
    }

    public void setRounded(boolean rounded) {
        this.rounded = rounded;
    }

    /**
     * Paints the button.
     *
     * @param g the Graphics object
     */
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
        if (rounded) {
            g.fillRoundRect(1, 1, getWidth() - 1, getHeight() - 1, 12, 12);
        } else {
            g.fillRect(1, 1, getWidth() - 1, getHeight() - 1);
        }
        g.setColor(c);

        if (rounded) {
            // Draw a rounded rectangle in the background of the button
            g.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 10, 10);
        } else {
            g.fillRect(0, 0, getWidth() - 3, getHeight() - 3);
        }

        super.paintComponent(g);
    }

}
