package ui.passenger;

import ui.round.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmPaymentPage {
    private JFrame frame;
    private JPanel panel;
    private JLabel headerLabel, totalCostLabel;
    private JButton confirmButton, cancelButton;

    public ConfirmPaymentPage() {

        // Create the frame and panel
        frame = new JFrame("Confirm Payment");
        frame.setPreferredSize(new Dimension(900, 600));
        panel = new JPanel(new GridLayout(0, 3));

        // Create the header label
        headerLabel = new JLabel("Confirm Payment?", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 32));

        // Create the total cost label
        // bruh how do i get the total cost from the previous page
        totalCostLabel = new JLabel("Total Cost: $0.00");
        totalCostLabel.setFont(new Font("Serif", Font.BOLD, 28));
        totalCostLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create the confirm button
        confirmButton = new RoundedButton("Confirm");
        confirmButton.setFont(new Font("Serif", Font.BOLD, 28));
        confirmButton.setPreferredSize(new Dimension(150, 50));
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setBackground(new Color(0, 151, 8));
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ThankYouPage();
                frame.dispose();
            }
        });

        // Create the cancel button
        cancelButton = new RoundedButton("Cancel");
        cancelButton.setFont(new Font("Serif", Font.BOLD, 28));
        cancelButton.setPreferredSize(new Dimension(150, 50));
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setBackground(new Color(172, 64, 58));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PurchaseTicketPage();
                frame.dispose();
            }
        });

        // Add components to the panel
        panel.setBackground(new Color(210, 207, 206));

        // Line 1: empty
        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(new JLabel("  "));
        panel.add(headerLabel);
        panel.add(new JLabel("  "));

        panel.add(new JLabel("  "));
        panel.add(totalCostLabel);  // Add total cost label
        panel.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        panel.add(cancelButton);
        panel.add(new JLabel("  "));
        panel.add(confirmButton);

        // add panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ConfirmPaymentPage();
    }

}
