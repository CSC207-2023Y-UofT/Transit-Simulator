package ui.round;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class SuppliedLabel extends JLabel {

    private final Supplier<String> textSupplier;

    public SuppliedLabel(Supplier<String> textSupplier) {
        super("");
        this.textSupplier = textSupplier;
    }

    @Override
    protected void paintComponent(Graphics g) {
        setText(textSupplier.get());
        super.paintComponent(g);
    }
}
