package ui.util;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

/**
 * Border that puts a shadow on whatever other component
 * it is applied to.
 */
public class ShadowBorder extends AbstractBorder {
    /**
     * The thickness of the border in pixels
     */
    private final int thickness = 8;
    /**
     * The colour of the border
     */
    private final Color colour = new Color(0, 0, 0, 55);


    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

        Color oldColor = g.getColor();

        for (int i = 0; i <= thickness; i++) {
            Color editedColour = new Color(
                    colour.getRed(),
                    colour.getGreen(),
                    colour.getBlue(),
                    (int) ((double) i / thickness * colour.getAlpha())
            );
            g.setColor(editedColour);
            g.drawRect(x + i, y + i, width - i * 2 - 1, height - i * 2 - 1);
        }

        g.setColor(oldColor);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }
}
