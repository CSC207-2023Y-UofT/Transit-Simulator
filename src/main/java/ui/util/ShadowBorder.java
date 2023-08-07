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
    private int thickness = 8;
    /**
     * The colour of the border
     */
    private final Color colour = new Color(0, 0, 0, 55);
    private int extraInset;
    private int extraInset1;
    private int extraInset2;
    private int extraInset3;


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
            g.drawRect(x + i + extraInset1,
                    y + i + extraInset,
                    width - i * 2 - 1 - extraInset3 - extraInset1,
                    height - i * 2 - 1 - extraInset2 - extraInset);
        }

        g.setColor(oldColor);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness + extraInset,
                thickness + extraInset1,
                thickness + extraInset2,
                thickness + extraInset3);
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public void setExtraInsets(int extraInset, int extraInset1, int extraInset2, int extraInset3) {
        this.extraInset = extraInset;
        this.extraInset1 = extraInset1;
        this.extraInset2 = extraInset2;
        this.extraInset3 = extraInset3;
    }
}
