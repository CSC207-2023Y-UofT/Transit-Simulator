package ui.passenger;

import ui.UIController;
import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmPaymentPage extends JPanel {

    public ConfirmPaymentPage(UIController controller) {
        super(new GridLayout(0, 3));

        // Create the header label
        JLabel headerLabel = new JLabel("Confirm Payment?", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 32));

        // Create the total cost label
        // bruh how do i get the total cost from the previous page
        JLabel totalCostLabel = new JLabel("Total Cost: $0.00");
        totalCostLabel.setFont(new Font("Serif", Font.BOLD, 28));
        totalCostLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create the confirm button
        JButton confirmButton = new RoundedButton("Confirm");
        confirmButton.setFont(new Font("Serif", Font.BOLD, 28));
        confirmButton.setPreferredSize(new Dimension(150, 50));
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setBackground(new Color(0, 151, 8));
        confirmButton.addActionListener(e -> controller.open(new ThankYouPage(controller)));

        // Create the cancel button
        JButton cancelButton = new RoundedButton("Cancel");
        cancelButton.setFont(new Font("Serif", Font.BOLD, 28));
        cancelButton.setPreferredSize(new Dimension(150, 50));
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setBackground(new Color(172, 64, 58));
        cancelButton.addActionListener(e -> controller.open(new PurchaseTicketPage(controller)));

        // Add components to the panel
        this.setBackground(new Color(210, 207, 206));

        // Line 1: empty
        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(new JLabel("  "));
        this.add(headerLabel);
        this.add(new JLabel("  "));

        this.add(new JLabel("  "));
        this.add(totalCostLabel);  // Add total cost label
        this.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            this.add(new JLabel("  "));
        }

        this.add(cancelButton);
        this.add(new JLabel("  "));
        this.add(confirmButton);
    }

}
