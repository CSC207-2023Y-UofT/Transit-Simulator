package ui.util;

import javax.swing.*;
import java.awt.*;

public class ShadowPanel extends JPanel {

    private final ShadowBorder shadowBorder;

    public ShadowPanel(LayoutManager layoutManager) {
        super(layoutManager);
        setBackground(new Color(0, 0, 0, 0));
        shadowBorder = new ShadowBorder();
        setBorder(shadowBorder);
    }

    public ShadowPanel() {
        super();
        setBackground(new Color(0, 0, 0, 0));
        shadowBorder = new ShadowBorder();
        setBorder(shadowBorder);
    }

    public void setThickness(int thickness) {
        shadowBorder.setThickness(thickness);
    }
}
