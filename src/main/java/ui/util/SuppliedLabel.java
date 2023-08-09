package ui.util;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

/**
 * SuppliedLabel is a JLabel that gets its text from a Supplier.
 */
public class SuppliedLabel extends JLabel {

    /**
     * The Supplier that is used to get the text.
     */
    private final Supplier<String> textSupplier;

    /**
     * Constructs a new SuppliedLabel object with the given Supplier.
     *
     * @param textSupplier the Supplier that is used to get the text
     */
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
