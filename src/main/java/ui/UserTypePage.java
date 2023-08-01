package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserTypePage {
    private JFrame frame;
    private JPanel panel;
    private JLabel headerLabel, userTypeLabel;
    private JButton passengerButton, staffButton;


    public UserTypePage() {

        // Create the frame and panel
        frame = new JFrame("Ticket Buying Page");
        panel = new JPanel(new GridLayout(0, 3));

        // Add the "Please select your user type." title
        userTypeLabel = new JLabel("Please select your user type.", SwingConstants.CENTER);
        userTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the label

        // Passenger button
        passengerButton = new JButton("Passenger");
        passengerButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the button
        passengerButton.setBackground(Color.GREEN);
        passengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the ticket buying page and dispose of the current frame
                new TicketBuyingPage();
                frame.dispose();
            }
        });

        // Staff button
        staffButton = new JButton("Staff");
        staffButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center align the button
        staffButton.setBackground(Color.GREEN);
        staffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display a message saying "Not implemented yet"
                JOptionPane.showMessageDialog(frame, "Not implemented yet.");
            }
        });

        // Add components to the panel

        panel.add(new JLabel("  "));
        panel.add(userTypeLabel);
        panel.add(new JLabel("  "));

        panel.add(new JLabel("  "));
        panel.add(passengerButton);
        panel.add(new JLabel("  "));

        panel.add(new JLabel("  "));
        panel.add(staffButton);
        panel.add(new JLabel("  "));

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel("  "));
        }

        // Add panel to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new UserTypePage();
    }
}
