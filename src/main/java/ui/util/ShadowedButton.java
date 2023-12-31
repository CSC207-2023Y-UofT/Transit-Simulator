package ui.util;

import javax.swing.*;
import java.awt.*;

/**
 * Shadowed is a JButton that is rounded and has a shadow.
 */
public class ShadowedButton extends JButton {

    /**
     * Whether the button is rounded.
     */
    private boolean rounded = true;

    /**
     * Constructs a new ShadowedButton with the given label.
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

    /**
     * Sets whether the button is rounded.
     *
     * @param rounded whether the button is rounded
     */
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
        // The thickness of the shadow.
        final int shadowThickness = 6;

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
        Color colour = new Color(0, 0, 0, 40);

        for (int i = 0; i < shadowThickness; i++) {
            Color edited = new Color(
                    colour.getRed(),
                    colour.getGreen(),
                    colour.getBlue(),
                    (int) ((double) i / shadowThickness * colour.getAlpha())
            );
            g.setColor(edited);
            if (rounded) {
                g.fillRoundRect(i, i, getWidth() - i * 2 - 1, getHeight() - i * 2 - 1, 10, 10);
            } else {
                g.fillRect(i, i, getWidth() - i * 2 - 1, getHeight() - i * 2 - 1);
            }
        }
        g.setColor(c);

        if (rounded) {
            // Draw a rounded rectangle in the background of the button
            g.fillRoundRect(shadowThickness, shadowThickness, getWidth() - shadowThickness * 2, getHeight() - shadowThickness * 2, 10, 10);
        } else {
            g.fillRect(shadowThickness, shadowThickness, getWidth() - shadowThickness * 2, getHeight() - shadowThickness * 2);
        }

        g.setColor(getForeground());
        super.paintComponent(g);
    }

}
