package ui.util;

import java.awt.*;
import java.util.function.Supplier;

public class SuppliedRoundLabel extends RoundedLabel {

    private final Supplier<String> textSupplier;

    public SuppliedRoundLabel(Supplier<String> textSupplier) {
        super("");
        this.textSupplier = textSupplier;
    }

    @Override
    protected void paintComponent(Graphics g) {
        setText(textSupplier.get());
        super.paintComponent(g);
    }
}
