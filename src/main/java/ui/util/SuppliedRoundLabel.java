package ui.util;

import java.awt.*;
import java.util.function.Supplier;

/**
 * SuppliedRoundLabel is a RoundedLabel that gets its text from a Supplier.
 */
public class SuppliedRoundLabel extends RoundedLabel {

    /**
     * The Supplier that is used to get the text.
     */
    private final Supplier<String> textSupplier;

    /**
     * Constructs a new SuppliedRoundLabel object with the given Supplier.
     *
     * @param textSupplier the Supplier that is used to get the text
     */
    public SuppliedRoundLabel(Supplier<String> textSupplier) {
        super("");
        this.textSupplier = textSupplier;
    }

    // Inherited javadoc
    @Override
    protected void paintComponent(Graphics g) {
        setText(textSupplier.get());
        super.paintComponent(g);
    }

}
