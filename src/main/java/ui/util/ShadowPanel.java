package ui.util;

import javax.swing.*;
import java.awt.*;

/**
 * ShadowPanel is a JPanel that has a shadow.
 */
public class ShadowPanel extends JPanel {

    /**
     * The ShadowBorder that is used to draw the shadow.
     */
    private final ShadowBorder shadowBorder;

    /**
     * Constructs a new ShadowPanel object with the given LayoutManager.
     *
     * @param layoutManager the LayoutManager that is used to layout the components
     */
    public ShadowPanel(LayoutManager layoutManager) {
        super(layoutManager);
        setBackground(new Color(0, 0, 0, 0));
        shadowBorder = new ShadowBorder();
        setBorder(shadowBorder);
    }

    /**
     * Constructs a new ShadowPanel object with the default FlowLayout.
     */
    public ShadowPanel() {
        super();
        setBackground(new Color(0, 0, 0, 0));
        shadowBorder = new ShadowBorder();
        setBorder(shadowBorder);
    }

    /**
     * Sets the extra inset of the shadow.
     *
     * @param extraInset the extra inset of the shadow
     */
    public void setExtraInset(int extraInset) {
        shadowBorder.setExtraInsets(extraInset, extraInset, extraInset, extraInset);
    }

    /**
     * Sets the thickness of the shadow border.
     *
     * @param thickness the thickeness of the shadow border.
     */
    public void setThickness(int thickness) {
        shadowBorder.setThickness(thickness);
    }
}
