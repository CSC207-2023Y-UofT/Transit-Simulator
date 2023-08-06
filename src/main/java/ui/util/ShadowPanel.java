package ui.util;

import javax.swing.*;
import java.awt.*;

public class ShadowPanel extends JPanel {

    public ShadowPanel(LayoutManager layoutManager) {
        super(layoutManager);
        setBackground(new Color(0, 0, 0, 0));
        setBorder(new ShadowBorder());
    }
    public ShadowPanel() {
        super();
        setBackground(new Color(0, 0, 0, 0));
        setBorder(new ShadowBorder());
    }
}
