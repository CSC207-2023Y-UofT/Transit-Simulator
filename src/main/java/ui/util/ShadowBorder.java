package ui.util;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

/**
 * Border that puts a shadow on whatever other component it is applied to.
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

    /**
     * The extra insets of the border
     */
    private int extraInset, extraInset1, extraInset2, extraInset3;

    // Inherited javadoc
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

    // Inherited javadoc
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness + extraInset,
                thickness + extraInset1,
                thickness + extraInset2,
                thickness + extraInset3);
    }

    /**
     * Sets the thickness of the border.
     *
     * @param thickness the thickness of the border
     */
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    /**
     * Sets the extra insets of the border.
     *
     * @param extraInset  the extra inset
     * @param extraInset1 the extra inset 1
     * @param extraInset2 the extra inset 2
     * @param extraInset3 the extra inset 3
     */
    public void setExtraInsets(int extraInset, int extraInset1, int extraInset2, int extraInset3) {
        this.extraInset = extraInset;
        this.extraInset1 = extraInset1;
        this.extraInset2 = extraInset2;
        this.extraInset3 = extraInset3;
    }

}
