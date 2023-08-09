package ui.util;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class SuppliedLabel extends JLabel {

    private final Supplier<String> textSupplier;

    public SuppliedLabel(Supplier<String> textSupplier) {
        super("");
        this.textSupplier = textSupplier;
    }

    public void update() {
        setText(textSupplier.get());
    }

    @Override
    protected void paintComponent(Graphics g) {
        update();
        super.paintComponent(g);
    }
}
